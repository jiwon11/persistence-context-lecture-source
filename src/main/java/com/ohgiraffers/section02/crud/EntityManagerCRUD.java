package com.ohgiraffers.section02.crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntityManagerCRUD {

    private EntityManager manager;

    public Menu findMenuByMenuCode(int menuCode) {

        manager = EntityManagerGenerator.getManagerInstance();

        return manager.find(Menu.class, menuCode);
    }

    public Long saveAndReturnAllCount(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        manager = EntityManagerGenerator.getManagerInstance();


        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(new Menu(menuName, menuPrice, categoryCode, orderableStatus));
        // 영속성 컨텍스트에 새로운 엔티티를 추가
        manager.flush();

//        transaction.commit();


        return getCount(manager);
    }

    private Long getCount(EntityManager manager) {
        return manager.createQuery("select count(*) from Section02Menu", Long.class).getSingleResult();
    }

    public EntityManager getManagerInstance() {

        return manager;
    }

    public Menu modifyMenuName(int menuCode, String menuName) {

        Menu menu = findMenuByMenuCode(menuCode);
        menu.setMenuName(menuName);

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.flush(); // 변경된 이름을 처리하라는 SQL을 전송

        return menu;
    }

    public Long removeAndReturnAllCount(int menuCode) {

        Menu menu = findMenuByMenuCode(menuCode);

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.remove(menu);
        manager.flush();

        return getCount(manager);
    }
}
