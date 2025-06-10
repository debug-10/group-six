#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
const uint8_t FAN_PWM_PIN = D1;
const uint8_t FAN_DIR_PIN = D2;   


const uint8_t LED_R_PIN = D6;    
const uint8_t LED_G_PIN = D4;    
const uint8_t LED_B_PIN = D5;     

// WiFi和MQTT配置
const char* WIFI_SSID = "wus-over-net";
const char* WIFI_PASS = "12345678";
const char* MQTT_SERVER = "119.91.199.84";
const uint16_t MQTT_PORT = 1883;
const char* MQTT_USER = "admin";
const char* MQTT_PASS = "public";

WiFiClient networkClient;
PubSubClient mqttClient(networkClient);

bool fanOn = false;
uint8_t fanSpeed = 0;
uint8_t ledR = 255, ledG = 255, ledB = 255;

// 定时关闭相关变量
bool timerActive = false;
unsigned long timerDuration = 0; // 定时持续时间（毫秒）
unsigned long timerStartTime = 0; // 定时开始时间

String fanControlTopic;
String fanStatusTopic;
String ledControlTopic;
String ledStatusTopic;

String generateDeviceID(uint8_t pin);
void setupNetwork();
void connectMQTT();
void setFan(uint8_t speed);
void setLED(uint8_t r, uint8_t g, uint8_t b);
void publishFanStatus();
void publishLEDStatus();
void handleMQTTMessage(char* topic, byte* payload, unsigned int length);
void checkTimer(); // 新增：检查定时器状态

void rgbFade(int redpin, int greenpin, int bluepin) {
  int val;
  for (val = 0; val < 255; val++) {
    analogWrite(redpin, val);
    analogWrite(greenpin, val);
    analogWrite(bluepin, 255 - val);
    delay(10);
  }
  for (val = 255; val > 0; val--) {
    analogWrite(redpin, val);
    analogWrite(greenpin, val);
    analogWrite(bluepin, 255 - val);
    delay(10);
  }
}

String generateDeviceID(uint8_t pin)
{
  String baseID = WiFi.macAddress();
  baseID.replace(":", "");
  return "dev_" + baseID + "_" + String(pin);
}

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
    
    String fan_id = generateDeviceID(FAN_PWM_PIN);
    String led_id = generateDeviceID(LED_R_PIN);
    
    fanControlTopic = "device/" + fan_id + "/control";
    fanStatusTopic = "device/" + fan_id + "/status";
    ledControlTopic = "device/" + led_id + "/control";
    ledStatusTopic = "device/" + led_id + "/status";

    Serial.println("\n=== 主题配置 ===");
    Serial.println("风扇控制主题: " + fanControlTopic);
    Serial.println("风扇状态主题: " + fanStatusTopic);
    Serial.println("LED控制主题: " + ledControlTopic);
    Serial.println("LED状态主题: " + ledStatusTopic);
  } else {
    Serial.println("\n连接失败!");
    ESP.deepSleep(0);
  }
}

// MQTT连接
void connectMQTT() {
  Serial.println("\n连接MQTT...");
  
  while (!mqttClient.connected()) {
    String clientId = "Controller-" + String(random(0xFFFF), HEX);
    
    if (mqttClient.connect(clientId.c_str(), MQTT_USER, MQTT_PASS)) {
      Serial.println("MQTT已连接!");
      mqttClient.subscribe(fanControlTopic.c_str());
      mqttClient.subscribe(ledControlTopic.c_str());
      publishFanStatus(); 
      publishLEDStatus(); 
    } else {
      Serial.printf("连接失败[%d], 5秒后重试...\n", mqttClient.state());
      delay(5000);
    }
  }
}

void setFan(uint8_t speed) {
  if (speed > 0) {
    fanOn = true;
    fanSpeed = speed;

    analogWrite(FAN_PWM_PIN, fanSpeed);

    Serial.printf("[FAN] 已开启，速度: %d\n", fanSpeed);
  } else {
    fanOn = false;
    fanSpeed = 0;
    analogWrite(FAN_PWM_PIN, 0);

    Serial.println("[FAN] 已关闭");
  }
}
void setLED(uint8_t r, uint8_t g, uint8_t b) {
  ledR = r;
  ledG = g;
  ledB = b;
  
  analogWrite(LED_R_PIN, 255 - r);
  analogWrite(LED_G_PIN, 255 - g);
  analogWrite(LED_B_PIN, 255 - b);

  Serial.printf("[LED] 设置颜色: R=%d, G=%d, B=%d\n", r, g, b);
}

void publishFanStatus() {
  StaticJsonDocument<256> doc;
  
  JsonObject fan = doc.createNestedObject("fan");
  fan["power"] = fanSpeed > 0 ? "ON" : "OFF";  
  fan["speed"] = fanSpeed;
  
  // 添加定时信息
  if (timerActive) {
    unsigned long remainingTime = 0;
    if (millis() >= timerStartTime) {
      unsigned long elapsedTime = millis() - timerStartTime;
      if (elapsedTime < timerDuration) {
        remainingTime = (timerDuration - elapsedTime) / 1000; // 转换为秒
      }
    }
    fan["timer"] = remainingTime;
  } else {
    fan["timer"] = 0;
  }
  
  char json[256];
  serializeJson(doc, json);

  Serial.println("\n[Fan Status] 当前风扇状态：");
  Serial.println(json);

  if (mqttClient.publish(fanStatusTopic.c_str(), json)) {
    Serial.println("[Fan Status] 已发布风扇状态更新");
  }
}

void publishLEDStatus() {
  StaticJsonDocument<256> doc;

  JsonObject led = doc.createNestedObject("led");
  led["r"] = ledR;
  led["g"] = ledG;
  led["b"] = ledB;
  
  char json[256];
  serializeJson(doc, json);

  Serial.println("\n[LED Status] 当前LED状态：");
  Serial.println(json);

  if (mqttClient.publish(ledStatusTopic.c_str(), json)) {
    Serial.println("[LED Status] 已发布LED状态更新");
  }
}

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
  if (String(topic) == fanControlTopic) {
    // 处理风扇速度设置
    if (doc.containsKey("speed")) {
      uint8_t speed = doc["speed"];
      setFan(speed);
    }
    
    // 处理定时设置
    if (doc.containsKey("timer")) {
      int timerMinutes = doc["timer"].as<int>();
      if (timerMinutes > 0) {
        timerDuration = (unsigned long)timerMinutes * 60 * 1000; // 转换为毫秒
        timerStartTime = millis();
        timerActive = true;
        
        // 如果风扇是关的，打开风扇（设置为中速）
        if (fanSpeed == 0) {
          setFan(128); // 设置为中速
        }
        
        Serial.printf("[控制] 设置风扇定时: %d 分钟\n", timerMinutes);
      } else {
        timerActive = false;
        Serial.println("[控制] 取消风扇定时");
      }
    }
  } 
  else if (String(topic) == ledControlTopic) {
    uint8_t r = doc["r"] | ledR;
    uint8_t g = doc["g"] | ledG;
    uint8_t b = doc["b"] | ledB;
    setLED(r, g, b); 
  }
  
  publishFanStatus();
  publishLEDStatus();
}

// 检查定时器状态
void checkTimer() {
  if (timerActive) {
    unsigned long elapsedTime = millis() - timerStartTime;
    if (elapsedTime >= timerDuration) {
      // 定时结束，关闭风扇
      setFan(0);
      timerActive = false;
      Serial.println("[定时] 定时结束，关闭风扇");
      publishFanStatus(); // 发布状态更新
    }
  }
}

void setup() {
  Serial.begin(115200);
  Serial.println("\n=== 智能设备控制器 ===");
  pinMode(FAN_PWM_PIN, OUTPUT);
  pinMode(FAN_DIR_PIN, OUTPUT);
  pinMode(LED_R_PIN, OUTPUT);
  pinMode(LED_G_PIN, OUTPUT);
  pinMode(LED_B_PIN, OUTPUT);
  rgbFade(LED_R_PIN, LED_G_PIN, LED_B_PIN);
  setupNetwork();
  mqttClient.setServer(MQTT_SERVER, MQTT_PORT);
  mqttClient.setCallback(handleMQTTMessage);
}

void loop() {
  if (!mqttClient.connected()) {
    connectMQTT();
  }
  mqttClient.loop();
  
  // 检查定时器状态
  checkTimer();
  
  static unsigned long lastUpdateTime = 0;
  const unsigned long UPDATE_INTERVAL = 30000; 
  if (millis() - lastUpdateTime > UPDATE_INTERVAL) {
    publishFanStatus();
    publishLEDStatus(); 
    lastUpdateTime = millis();
  }
}
