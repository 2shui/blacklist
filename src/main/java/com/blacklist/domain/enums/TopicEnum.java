package com.blacklist.domain.enums;

public class TopicEnum {
	public enum Status {
		NORMAL(1, "正常"), CANCEL(2, "撤销");
		private Integer value;
		private String des;

		Status(Integer value, String des) {
			this.value = value;
			this.des = des;
		}

		public Integer getValue() {
			return value;
		}

		public String getDes() {
			return des;
		}
	}
}
