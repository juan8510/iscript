package com.lezo.iscript.utils;

public class SampleDto {
	private String name;
	private Integer age;
	private Boolean bMan;
	private Float height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getbMan() {
		return bMan;
	}

	public void setbMan(Boolean bMan) {
		this.bMan = bMan;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "SampleDto [name=" + name + ", age=" + age + ", bMan=" + bMan + ", height=" + height + "]";
	}
}