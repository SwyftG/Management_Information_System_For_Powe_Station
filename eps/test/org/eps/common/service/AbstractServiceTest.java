package org.eps.common.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/beans.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class AbstractServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

}
