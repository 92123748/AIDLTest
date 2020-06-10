package com.study.stydyfirst.server;

import android.os.IInterface;
import android.os.RemoteException;

import com.study.stydyfirst.Bean.PersonBean;

import java.util.List;

/**
 * 这个类用来定义服务端具有什么样的能力
 */
public interface PersonManager extends IInterface {
    /**
     * 添加人数
     *
     * @throws RemoteException
     */
    void addPerson(PersonBean personBean) throws RemoteException;

    /**
     * 删除人数
     *
     * @throws RemoteException
     */
    void deletePerson(PersonBean personBean) throws RemoteException;

    /**
     * 获取人数
     *
     * @throws RemoteException
     */
    List<PersonBean> getPersons() throws RemoteException;
}
