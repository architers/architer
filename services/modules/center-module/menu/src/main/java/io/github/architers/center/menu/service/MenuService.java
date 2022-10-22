package io.github.architers.center.menu.service;

import io.github.architers.center.menu.utils.NodeTreeUtils;

import java.util.List;

public interface MenuService {
    List<NodeTreeUtils.TreeNode> getMenuTreeWithPrivilege();
}
