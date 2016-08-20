/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.init;



import java.io.File;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.SizeOfPolicyConfiguration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.StorableBean;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.account.AcountDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.servlet.util.PropertiesConfig;


/**
 * 
 * @author peter
 *
 */
public class CacheInit extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3008204545799439966L;

	protected AcountDAO accountDAO;
   
    private CacheManager cacheManager;
    
    private SizeOfPolicyConfiguration sizeOfPolicyConfiguration;

    private Logger logger = Logger.getLogger(this.getClass());
    
    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        accountDAO = AcountDAO.getInstance();
        sizeOfPolicyConfiguration = new SizeOfPolicyConfiguration();
        sizeOfPolicyConfiguration.setMaxDepthExceededBehavior("abort");
        logger.info("Starting to initialize cache");
        initCache();
        logger.info("Have finished initializing cache");
    }
    

    /**
     *
     */
    protected void initCache() {
        DiskStoreConfiguration diskConfig = new DiskStoreConfiguration();
        diskConfig.setPath(System.getProperty("java.io.tmpdir") + File.separator +
        		"ehcache" + File.separator + PropertiesConfig.getConfigValue("CACHE_FILE"));        
        
        Configuration config = (new Configuration()).diskStore(diskConfig);
        config.setMaxBytesLocalHeap(Long.parseLong(PropertiesConfig.getConfigValue("MAX_BYTES_LOCAL_HEAP")));
        config.setMaxBytesLocalDisk(Long.parseLong(PropertiesConfig.getConfigValue("MAX_BYTES_LOCAL_DISK")));
        config.setUpdateCheck(false);

        cacheManager = CacheManager.create(config);

        List<? extends StorableBean> objList;

        objList = accountDAO.getAllAccounts();
        initCacheByUuid(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID, objList);
       
        initAccountsCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID);
           
        initGenericCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID);
              
    }
    

    /**
     *
     * @param cacheName
     * @param objList
     */
    private void initCacheByUuid(String cacheName, List<? extends StorableBean> objList) {
    	Cache cache = null;
        if (!cacheManager.cacheExists(cacheName)) {
            CacheConfiguration cacheConfig = new CacheConfiguration().sizeOfPolicy(sizeOfPolicyConfiguration);
            cacheConfig.setCopyOnRead(false); // Whether the Cache should copy elements it returns
            cacheConfig.setCopyOnWrite(false); // Whether the Cache should copy elements it gets
            cacheConfig.setEternal(true); // Sets whether elements are eternal.        
            cacheConfig.setName(cacheName); // Sets the name of the cache.

            cache = new Cache(cacheConfig);
            cacheManager.addCacheIfAbsent(cache);
            if (cache.getStatus() == Status.STATUS_UNINITIALISED) {
                cache.initialise();
            }
            
        } else {
        	CacheManager mgr = CacheManager.getInstance();
        	cache = mgr.getCache(cacheName);
        }
     
        for (StorableBean b : objList) {
            cache.put(new Element(b.getUuid(), b)); // UUID as the key            
        }
    }    
    

    /**
     *
     * @param cacheName
     */
    private void initAccountsCache(String cacheName) {

        if (!cacheManager.cacheExists(cacheName)) {
            CacheConfiguration cacheConfig = new CacheConfiguration().sizeOfPolicy(sizeOfPolicyConfiguration);
            cacheConfig.setCopyOnRead(false); // Whether the Cache should copy elements it returns
            cacheConfig.setCopyOnWrite(false); // Whether the Cache should copy elements it gets
            cacheConfig.setEternal(true); // Sets whether elements are eternal.    	
            cacheConfig.setName(cacheName); // Sets the name of the cache.

            Cache accountsCache = new Cache(cacheConfig);
            cacheManager.addCacheIfAbsent(accountsCache);
            if (accountsCache.getStatus() == Status.STATUS_UNINITIALISED) {
                accountsCache.initialise();
            }

            List<Account> allAccounts = accountDAO.getAllAccounts();

            if (StringUtils.equals(cacheName, CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID)) {
                for (Account a : allAccounts) {
                    accountsCache.put(new Element(a.getUuid(), a));		// Uuid as the key
                }
            }
        }
    }

    
    
    /**
     *
     * @param cacheName
     */
    private void initGenericCache(String cacheName) {
        if (!cacheManager.cacheExists(cacheName)) {
            CacheConfiguration cacheConfig = new CacheConfiguration().sizeOfPolicy(sizeOfPolicyConfiguration);
            cacheConfig.setCopyOnRead(false); // Whether the Cache should copy elements it returns
            cacheConfig.setCopyOnWrite(false); // Whether the Cache should copy elements it gets
            cacheConfig.setEternal(true); // Sets whether elements are eternal.    	
            cacheConfig.setName(cacheName); // Sets the name of the cache.

            Cache cache = new Cache(cacheConfig);
            cacheManager.addCacheIfAbsent(cache);
            if (cache.getStatus() == Status.STATUS_UNINITIALISED) {
                cache.initialise();
            }
        }
    }
    
    
    /**
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();

        CacheManager.getInstance().shutdown();
    }
}
