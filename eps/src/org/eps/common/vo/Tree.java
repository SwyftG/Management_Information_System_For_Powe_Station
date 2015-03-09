package org.eps.common.vo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Tree implements Serializable {

	private static final long serialVersionUID = 8117546412968384647L;

	private long id;

	private String text;

	private String iconCls;

	private String state;

	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	private Queue<Tree> children = new LinkedList<Tree>();
	
	private boolean checked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Queue<Tree> getChildren() {
		return children;
	}

	public void setChildren(Queue<Tree> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}