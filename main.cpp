#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

// 引脚定义
#define LIGHT_SENSOR_PIN A0
#define LED_R D0
#define LED_G D1
#define LED_B D2
#define PIR_PIN D6

// 阈值和超时设置
const int LIGHT_THRESHOLD = 50;
const unsigned long PIR_INIT_TIMEOUT = 10000;
const unsigned long UPDATE_INTERVAL = 30000; // 状态上报间隔

// WiFi和MQTT配置
const char* WIFI_SSID = "wus-over-net";
const char* WIFI_PASS = "12345678";
const char* MQTT_SERVER = "119.91.199.84";
const uint16_t MQTT_PORT = 1883;
const char* MQTT_USER = "admin";
const char* MQTT_PASS = "public";

// 客户端实例化
WiFiClient networkClient;
PubSubClient mqttClient(networkClient);

// 状态变量
bool isInitialized = false;
unsigned long initStartTime = 0;
bool motionDetected = false;
bool autoMode = true; // 自动模式标志
bool alwaysOnMode = false; // 常亮模式标志
int ledR = 255, ledG = 255, ledB = 255; // 默认灯是关闭的 (255,255,255)
float currentLux = 0;

// 定时关闭相关变量
bool timerActive = false;
unsigned long timerDuration = 0; // 定时持续时间（毫秒）
unsigned long timerStartTime = 0; // 定时开始时间

// MQTT主题
String lightControlTopic;
String lightStatusTopic;

// 函数声明
void setupNetwork();
void connectMQTT();
void handleMQTTMessage(char* topic, byte* payload, unsigned int length);
void setColor(int r, int g, int b);
void setWarmWhite();
void turnOffLED();
void publishLightStatus();
String generateDeviceID(uint8_t pin);
void checkTimer();

// 生成设备ID
String generateDeviceID(uint8_t pin) {
  String baseID = WiFi.macAddress();
  baseID.replace(":", "");
  return "dev_" + baseID + "_" + String(pin);
}

// 设置网络连接
void setupNetwork() {
  Serial.println("\n连接WiFi...");
  WiFi.begin(WIFI_SSID, WIFI_PASS);

  unsigned long start = millis();
  while (WiFi.status() != WL_CONNECTED && millis() - start < 15000) {
    delay(500);
    Serial.print("...");
  }
  
  if (WiFi.status() == WL_CONNECTED) {
    Serial.printf("\n已连接! IP: %s\n", WiFi.localIP().toString().c_str());
    String light_id = generateDeviceID(LED_R);
    lightControlTopic = "device/" + light_id + "/control";
    lightStatusTopic = "device/" + light_id + "/status";
    Serial.println("\n=== 主题配置 ===");
    Serial.println("夜灯控制主题: " + lightControlTopic);
    Serial.println("夜灯状态主题: " + lightStatusTopic);
  } else {
    Serial.println("\n连接失败!");
    ESP.deepSleep(0);
  }
}

// 连接MQTT服务器
void connectMQTT() {
  Serial.println("\n连接MQTT...");
  while (!mqttClient.connected()) {
    String clientId = "NightLight-" + String(random(0xFFFF), HEX);
    
    if (mqttClient.connect(clientId.c_str(), MQTT_USER, MQTT_PASS)) {
      Serial.println("MQTT已连接!");
      mqttClient.subscribe(lightControlTopic.c_str());
      publishLightStatus();
    } else {
      Serial.printf("连接失败[%d], 5秒后重试...\n", mqttClient.state());
      delay(5000);
    }
  }
}

// 发布夜灯状态
void publishLightStatus() {
  StaticJsonDocument<256> doc;
  JsonObject light = doc.createNestedObject("light");
  light["power"] = (ledR == 255 && ledG == 255 && ledB == 255) ? "OFF" : "ON";
  light["r"] = 255 - ledR; // 反转值以符合用户界面逻辑
  light["g"] = 255 - ledG;
  light["b"] = 255 - ledB;
  light["auto"] = autoMode;
  light["alwaysOn"] = alwaysOnMode;
  light["lux"] = currentLux;
  light["motion"] = motionDetected;
  
  // 添加定时信息
  if (timerActive) {
    unsigned long remainingTime = 0;
    if (millis() >= timerStartTime) {
      unsigned long elapsedTime = millis() - timerStartTime;
      if (elapsedTime < timerDuration) {
        remainingTime = (timerDuration - elapsedTime) / 1000; // 转换为秒
      }
    }
    light["timer"] = remainingTime;
  } else {
    light["timer"] = 0;
  }
  
  char json[256];
  serializeJson(doc, json);
  Serial.println("\n[Light Status] 当前夜灯状态：");
  Serial.println(json);

  if (mqttClient.publish(lightStatusTopic.c_str(), json)) {
    Serial.println("[Light Status] 已发布夜灯状态更新");
  }
}

// 处理MQTT消息
void handleMQTTMessage(char* topic, byte* payload, unsigned int length) {
  char msg[length + 1];
  memcpy(msg, payload, length);
  msg[length] = '\0';
  Serial.printf("[MSG] 主题: %s 内容: %s\n", topic, msg);
  
  StaticJsonDocument<256> doc;
  if (deserializeJson(doc, msg)) {
    Serial.println("[ERROR] 无效JSON");
    return;
  }
  
  if (String(topic) == lightControlTopic) {
    // 处理自动/手动/常亮模式切换
    if (doc.containsKey("auto")) {
      autoMode = doc["auto"].as<bool>();
      if (autoMode) {
        alwaysOnMode = false; // 自动模式下关闭常亮
        timerActive = false;  // 自动模式下关闭定时
      }
      Serial.printf("[控制] 模式设置为: %s\n", autoMode ? "自动" : "手动");
    }
    
    // 处理常亮模式
    if (doc.containsKey("alwaysOn")) {
      alwaysOnMode = doc["alwaysOn"].as<bool>();
      if (alwaysOnMode) {
        autoMode = false; // 常亮模式下关闭自动模式
        setWarmWhite();  // 默认使用暖白色
        Serial.println("[控制] 设置为常亮模式");
      } else {
        Serial.println("[控制] 关闭常亮模式");
      }
    }
    
    // 处理定时设置
    if (doc.containsKey("timer")) {
      int timerMinutes = doc["timer"].as<int>();
      if (timerMinutes > 0) {
        timerDuration = (unsigned long)timerMinutes * 60 * 1000; // 转换为毫秒
        timerStartTime = millis();
        timerActive = true;
        autoMode = false; // 定时模式下关闭自动模式
        
        // 如果灯是关的，打开灯
        if (ledR == 255 && ledG == 255 && ledB == 255) {
          setWarmWhite();
        }
        
        Serial.printf("[控制] 设置定时: %d 分钟\n", timerMinutes);
      } else {
        timerActive = false;
        Serial.println("[控制] 取消定时");
      }
    }
    
    // 在手动模式下处理颜色设置
    if (!autoMode) {
      if (doc.containsKey("power")) {
        String power = doc["power"].as<String>();
        if (power == "ON") {
          // 如果指定了颜色，使用指定颜色，否则使用暖白色
          if (doc.containsKey("r") && doc.containsKey("g") && doc.containsKey("b")) {
            int r = 255 - doc["r"].as<int>(); // 反转值以符合硬件逻辑
            int g = 255 - doc["g"].as<int>();
            int b = 255 - doc["b"].as<int>();
            setColor(r, g, b);
            ledR = r;
            ledG = g;
            ledB = b;
            Serial.printf("[控制] 设置颜色: R=%d, G=%d, B=%d\n", 255-r, 255-g, 255-b);
          } else {
            setWarmWhite();
            Serial.println("[控制] 开灯 - 暖白色");
          }
        } else if (power == "OFF") {
          turnOffLED();
          alwaysOnMode = false; // 关灯时关闭常亮模式
          timerActive = false;  // 关灯时关闭定时
          Serial.println("[控制] 关灯");
        }
      } else if (doc.containsKey("r") && doc.containsKey("g") && doc.containsKey("b")) {
        // 直接设置颜色
        int r = 255 - doc["r"].as<int>(); // 反转值
        int g = 255 - doc["g"].as<int>();
        int b = 255 - doc["b"].as<int>();
        setColor(r, g, b);
        ledR = r;
        ledG = g;
        ledB = b;
        Serial.printf("[控制] 设置颜色: R=%d, G=%d, B=%d\n", 255-r, 255-g, 255-b);
      }
    }
  }
  
  // 发布更新后的状态
  publishLightStatus();
}

// 设置颜色
void setColor(int r, int g, int b) {
  analogWrite(LED_R, r);
  analogWrite(LED_G, g);
  analogWrite(LED_B, b);
  ledR = r;
  ledG = g;
  ledB = b;
}

// 设置暖白色
void setWarmWhite() {
  setColor(0, 0, 0); // 三色齐亮
}

// 关闭LED
void turnOffLED() {
  setColor(255, 255, 255); // 全灭
}

// 检查定时器状态
void checkTimer() {
  if (timerActive) {
    unsigned long elapsedTime = millis() - timerStartTime;
    if (elapsedTime >= timerDuration) {
      // 定时结束，关闭灯
      turnOffLED();
      timerActive = false;
      Serial.println("[定时] 定时结束，关闭灯光");
      publishLightStatus(); // 发布状态更新
    }
  }
}

void setup() {
  Serial.begin(115200);
  Serial.println("\n=== 智能夜灯控制器 ===");
  
  pinMode(LED_R, OUTPUT);
  pinMode(LED_G, OUTPUT);
  pinMode(LED_B, OUTPUT);
  pinMode(PIR_PIN, INPUT);
  
  turnOffLED();
  initStartTime = millis();
  Serial.println("系统启动，红外传感器初始化中...");
  
  // 设置网络连接
  setupNetwork();
  
  // 设置MQTT服务器
  mqttClient.setServer(MQTT_SERVER, MQTT_PORT);
  mqttClient.setCallback(handleMQTTMessage);
}

void loop() {
  // 检查MQTT连接
  if (!mqttClient.connected()) {
    connectMQTT();
  }
  mqttClient.loop();
  
  // 红外传感器初始化
  if (!isInitialized) {
    if (millis() - initStartTime >= PIR_INIT_TIMEOUT) {
      isInitialized = true;
      Serial.println("\n红外传感器初始化完成");
    } else {
      unsigned long remaining = PIR_INIT_TIMEOUT - (millis() - initStartTime);
      Serial.print("\r剩余时间: ");
      Serial.print(remaining / 1000);
      Serial.print(" 秒");
      delay(100);
      return;
    }
  }

  // 检查定时器状态
  checkTimer();

  static unsigned long lastCheckTime = 0;
  static unsigned long lastUpdateTime = 0;
  const unsigned long CHECK_INTERVAL = 500;

  // 检查传感器状态
  if (millis() - lastCheckTime >= CHECK_INTERVAL) {
    lastCheckTime = millis();

    motionDetected = digitalRead(PIR_PIN);
    int raw = analogRead(LIGHT_SENSOR_PIN);
    currentLux = raw * 0.0977;
    
    // 只在自动模式下根据传感器调整灯光，常亮模式和定时模式下不自动调整
    if (autoMode && !alwaysOnMode && !timerActive) {
      bool lightShouldBeOn = (currentLux > LIGHT_THRESHOLD) || motionDetected;
      
      if (lightShouldBeOn) {
        setWarmWhite();
        Serial.print("[灯光] 自动开启 - ");
        if (currentLux > LIGHT_THRESHOLD) {
          Serial.print("光照不足 (");
          Serial.print(currentLux, 1);
          Serial.print(" Lux)");
        }
        if (motionDetected) {
          Serial.print("有人在场");
        }
        Serial.println();
      } else {
        turnOffLED();
        Serial.print("[灯光] 自动关闭 - 光照充足且无人活动 (");
        Serial.print(currentLux, 1);
        Serial.println(" Lux)");
      }
    } else if (alwaysOnMode) {
      // 常亮模式下，确保灯是开着的
      if (ledR == 255 && ledG == 255 && ledB == 255) { // 如果灯是关的
        setWarmWhite();
        Serial.println("[灯光] 常亮模式 - 保持开启");
      }
    }

    Serial.print("状态 | 光照:");
    Serial.print(currentLux, 1);
    Serial.print(" Lux | PIR:");
    Serial.print(motionDetected ? "有人" : "无人");
    Serial.print(" | LED:");
    Serial.print((ledR == 255 && ledG == 255 && ledB == 255) ? "OFF" : "ON");
    Serial.print(" | 模式:");
    if (autoMode) {
      Serial.print("自动");
    } else if (alwaysOnMode) {
      Serial.print("常亮");
    } else if (timerActive) {
      unsigned long remainingSecs = 0;
      if (millis() >= timerStartTime) {
        unsigned long elapsedTime = millis() - timerStartTime;
        if (elapsedTime < timerDuration) {
          remainingSecs = (timerDuration - elapsedTime) / 1000;
        }
      }
      Serial.printf("定时(%lu秒)", remainingSecs);
    } else {
      Serial.print("手动");
    }
    Serial.println();
  }
  
  // 定期发布状态更新
  if (millis() - lastUpdateTime > UPDATE_INTERVAL) {
    lastUpdateTime = millis();
    publishLightStatus();
  }

  delay(100);
}