package com.sleaker.regional;

public enum Setting {
	STORAGE("storage.type", "yml"),
	SQL_NAME("storage.mysql.login", "minecraft"),
	SQL_PASS("storage.mysql.pass", "password"),
	SQL_URL("storage.mysql.url", "jdbc:mysql://localhost:3306/regions"),
	REGIONID("region.next-id", 1);
	
	public final String node;
	public final Object defaultVal;
	
	Setting(String node, Object defaultVal) {
		this.node = node;
		this.defaultVal = defaultVal;
	}
}
