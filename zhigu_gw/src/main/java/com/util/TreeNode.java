package com.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import net.sf.json.JSONObject;

/**
 * easui中的tree_data.json数据,只能有一个root节点 [{ "id":1, "text":"Folder1",
 * "iconCls":"icon-save", "children":[{ "text":"File1", "checked":true }] }]
 * 提供静态方法formatTree(List<TreeJson> list) 返回结果 TreeJson.formatTree(treeJsonlist)
 * 提供静态方法formatTreeFromList(List<TreeJson> list) 返回结果
 * TreeJson.formatTreeFromList(treeJsonlist)
 * 
 * @author zhangyongwei
 */
public class TreeNode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String parendId;
	private String text;
	private String url;// 资源路径
	private String iconCls;
	private String state;
	private String checked;
	private Integer sortNum;// 排序数字
	private JSONObject attributes = new JSONObject();
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParendId() {
		return parendId;
	}

	public void setParendId(String parendId) {
		this.parendId = parendId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public JSONObject getAttributes() {
		return attributes;
	}

	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// 第一种list转化树结构
	public static List<TreeNode> formatTree(List<TreeNode> list) {

		TreeNode root = new TreeNode();
		TreeNode node = new TreeNode();
		List<TreeNode> treelist = new ArrayList<TreeNode>();// 拼凑好的json格式的数据
		List<TreeNode> parentnodes = new ArrayList<TreeNode>();// parentnodes存放所有的父节点

		if (list != null && list.size() > 0) {
			root = list.get(0);
			// 循环遍历所有节点
			for (int i = 1; i < list.size(); i++) {
				node = list.get(i);
				if (node.getParendId().equals(root.getId())) {
					// 为tree root 增加子节点
					parentnodes.add(node);
					root.getChildren().add(node);
				} else {// 获取root子节点的孩子节点
					getChildrenNodes(parentnodes, node);
					parentnodes.add(node);
				}
			}
		}
		treelist.add(root);
		return treelist;

	}

	private static void getChildrenNodes(List<TreeNode> parentnodes, TreeNode node) {
		// 循环遍历所有父节点和node进行匹配，确定父子关系
		for (int i = parentnodes.size() - 1; i >= 0; i--) {

			TreeNode pnode = parentnodes.get(i);
			// 如果是父子关系，为父节点增加子节点，退出for循环
			if (pnode.getId().equals(node.getParendId())) {
				pnode.setState("closed");// 关闭二级树
				pnode.getChildren().add(node);
				return;
			} else {
			}
		}
	}

	// 第二种list转化树结构,默认有顶节点
	public static List<TreeNode> formatTreeFromList(List<TreeNode> list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		TreeNode upNode = new TreeNode();
		if (!CollectionUtils.isEmpty(list)) {
			upNode = list.get(0);
			upNode.setChildren(build(list, upNode));
		}
		treeList.add(upNode);
		return treeList;
	}

	private static List<TreeNode> build(List<TreeNode> list, TreeNode upNode) {
		List<TreeNode> childList = getChildrenNodes2(list, upNode);
		if (!CollectionUtils.isEmpty(childList)) {
			for (TreeNode childNode : childList) {
				childNode.setChildren(build(list, childNode));
			}
		}
		return childList;
	}

	private static List<TreeNode> getChildrenNodes2(List<TreeNode> list, TreeNode upNode) {
		List<TreeNode> childList = new ArrayList<TreeNode>();
		for (TreeNode childNode : list) {
			if (upNode.getId().equals(childNode.getParendId())) {
				childList.add(childNode);
			}
		}
		return childList;
	}

	// 第三种list转化树结构,默认无顶节点
	public static List<TreeNode> formatTreeFromListNoRootNode(List<TreeNode> list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (TreeNode upNode : list) {
			if ("1".equals(upNode.getParendId())) {
				upNode.setChildren(build(list, upNode));
				treeList.add(upNode);
			}
		}
		return treeList;
	}

	// 第四种list转化树结构,默认无顶节点，获取表中所有数据
	public static List<TreeNode> formatTreeFromListNoRootNode2(List<TreeNode> list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (TreeNode upNode : list) {
			// 拼接父节点为1的数据
			if ("1".equals(upNode.getParendId())) {
				upNode.setChildren(build(list, upNode));
				treeList.add(upNode);
			} else {
				// 拼接被删除父级节点的数据
				if (!"0".equals(upNode.getParendId())) {
					if (CollectionUtils.isEmpty(exceptRootNode(list, upNode))) {
						upNode.setParendId("1");
						upNode.setChildren(build(list, upNode));
						treeList.add(upNode);
					}
				}
			}
		}
		return treeList;
	}

	// 第四种list转化树结构,默认有顶节点，获取表中所有数据
	public static List<TreeNode> formatTreeFromListNoRootNode3(List<TreeNode> list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (TreeNode upNode : list) {
			// 拼接父节点为1的数据
			if ("0".equals(upNode.getParendId())) {
				upNode.setChildren(build(list, upNode));
				treeList.add(upNode);
			} else {
				// 拼接被删除父级节点的数据
				if (!"0".equals(upNode.getParendId())) {
					if (CollectionUtils.isEmpty(exceptRootNode(list, upNode))) {
						upNode.setParendId("1");
						upNode.setChildren(build(list, upNode));
						treeList.add(upNode);
					}
				}
			}
		}
		return treeList;
	}

	// 过滤有父级节点的数据
	private static List<TreeNode> exceptRootNode(List<TreeNode> list, TreeNode upNode) {
		List<TreeNode> childList = new ArrayList<TreeNode>();
		for (TreeNode childNode : list) {
			if (upNode.getParendId().equals(childNode.getId())) {
				childList.add(childNode);
				break;
			}
		}
		return childList;
	}
}