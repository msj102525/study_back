package org.myweb.first.test.model.vo;

public class TestVO implements java.io.Serializable {
	private static final long serialVersionUID = 3330259852242453317L;

	private String name;
	private int age;

	public TestVO() {
		super();
	}

	public TestVO(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TestVO [age=" + age + ", name=" + name + "]";
	}

}
