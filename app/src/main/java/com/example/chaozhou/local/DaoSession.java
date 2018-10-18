package com.example.chaozhou.local;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.chaozhou.local.table.Hotel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig hotelDaoConfig;

    private final HotelDao hotelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        hotelDaoConfig = daoConfigMap.get(HotelDao.class).clone();
        hotelDaoConfig.initIdentityScope(type);

        hotelDao = new HotelDao(hotelDaoConfig, this);

        registerDao(Hotel.class, hotelDao);
    }
    
    public void clear() {
        hotelDaoConfig.clearIdentityScope();
    }

    public HotelDao getHotelDao() {
        return hotelDao;
    }

}