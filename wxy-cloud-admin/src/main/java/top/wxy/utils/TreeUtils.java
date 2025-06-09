package top.wxy.utils;


import top.wxy.common.model.TreeNode;
import top.wxy.framework.common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类，如：菜单、机构等
 */
public class TreeUtils {

    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Integer pid) {
        AssertUtils.isNull(pid, "parentId");

        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(treeNode.getParentId())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }

        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getPkId().equals(treeNode.getParentId())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();
        Map<Integer, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for (T treeNode : treeNodes) {
            nodeMap.put(treeNode.getPkId(), treeNode);
        }

        for (T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getParentId());
            if (parent != null && !(node.getPkId().equals(parent.getPkId()))) {
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }

        return result;
    }

}