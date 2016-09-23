package com.snowy.demo.zcontacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactInfo implements Serializable {
	private static long successUpdataTime = 0;
	private String name;

	// 联系人电话信息
	public static class PhoneInfo implements Serializable {
		public int type;
		public String number;
		
	}

	// 联系人邮箱信息
	public static class EmailInfo implements Serializable {
		public int type;
		public String email;
	}

	// 联系人地址信息
	public static class AddressInfo implements Serializable {
		public int type;
		public String address;
	}

	private List<PhoneInfo> phoneList = new ArrayList<PhoneInfo>();
	private List<EmailInfo> emailList = new ArrayList<EmailInfo>();
	private List<AddressInfo> addressList = new ArrayList<AddressInfo>();

	public ContactInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ContactInfo setName(String name) {
		this.name = name;
		return this;
	}

	public List<PhoneInfo> getPhoneList() {
		return phoneList;
	}

	public ContactInfo setPhoneList(List<PhoneInfo> phoneList) {
		this.phoneList = phoneList;
		return this;
	}

	public List<EmailInfo> getEmailList() {
		return emailList;
	}

	public ContactInfo setEmailList(List<EmailInfo> emailList) {
		this.emailList = emailList;
		return this;
	}

	public List<AddressInfo> getAddressList() {
		return addressList;
	}

	public ContactInfo setAddressList(List<AddressInfo> addressList) {
		this.addressList = addressList;
		return this;
	}

	@Override
	public String toString() {
		return "{name: " + name + ", number: " + phoneList + ", email: "
				+ emailList + "}";
	}
}
