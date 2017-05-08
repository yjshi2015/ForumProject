package com.baobaotao.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *<br>
 * <b>������:</b>
 * 
 * <pre>
 * �����Ӧ��������
 * </pre>
 * 
 * @see
 *@since
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("2")
public class MainPost extends Post {

}
