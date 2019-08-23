package org.litespring.service.v3;

public class PetStoreService1 {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private Object version;

	public PetStoreService1(AccountDao accountDao, ItemDao itemDao, int version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}

	public PetStoreService1(AccountDao accountDao, ItemDao itemDao, String version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}

	public PetStoreService1(AccountDao accountDao, ItemDao itemDao, Object version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}
	
	public PetStoreService1(AccountDao accountDao, ItemDao itemDao, Boolean version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}
	
	public PetStoreService1(AccountDao accountDao, ItemDao itemDao, Integer version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}
	
	public AccountDao getAccountDao() {
		return accountDao;
	}
	
	public ItemDao getItemDao() {
		return itemDao;
	}
	
	public Object getVersion() {
		return version;
	}
}
