package org.litespring.service.v3;

public class PetStoreService {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private int version;

	public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
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

	public int getVersion() {
		return version;
	}

}
