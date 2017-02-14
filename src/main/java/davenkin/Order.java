package davenkin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 就是一次大的操作由不同的小操作组成，这些小的操作分布在不同的服务器上，且属于不同的应用，分布式事务需要保证这些小操作要么全部成功，
 * 要么全部失败。本质上来说，分布式事务就是为了保证不同数据库的数据一致性。
 * - 应用访问多个数据源
 * - 应用访问多个服务(子应用 -> 数据源)
 * ==========================================================================
 * XA(eXtended Architecture)是X/Open组织提出的采用两阶段提交方式来管理分布式事务的规范。
 * 定义了事务管理器(Transaction Manager)和局部资源管理器(Local Resource Manager)之间的接口。
 * 1. xa_open,xa_close：建立和关闭与资源管理器的连接。
 * 2. xa_start,xa_end：开始和结束一个本地事务。
 * 3. xa_prepare,xa_commit,xa_rollback：预提交、提交和回滚一个本地事务。
 * 4. xa_recover：回滚一个已进行预提交的事务。
 * 5. ax_开头的函数使资源管理器可以动态地在事务管理器中进行注册，并可以对XID(TRANSACTION IDS)进行操作。
 * 6. ax_reg,ax_unreg；允许一个资源管理器在一个TMS(TRANSACTION MANAGER SERVER)中动态注册或撤消注册。
 * XA性能不理想，无法满足高并发场景。XA目前在商业数据库支持的比较理想，在mysql数据库中支持的不太理想，许多nosql也没有支持XA，这让
 * XA的应用场景变得非常狭隘。
 *
 * ==========================================================================
 * 消息事务+最终一致性
 * http://www.cnblogs.com/zengkefu/p/5742617.html
 *
 * ==========================================================================
 * TCC模式
 * TCC编程模式，也是两阶段提交的一个变种。TCC提供了一个编程框架，将整个业务逻辑分为三块：Try、Confirm和Cancel三个操作。以在线下单
 * 为例，Try阶段会去扣库存，Confirm阶段则是去更新订单状态，如果更新订单失败，则进入Cancel阶段，会去恢复库存。总之，TCC就是通过代码
 * 人为实现了两阶段提交，不同的业务场景所写的代码都不一样，复杂度也不一样，因此，这种模式并不能很好地被复用。
 *
 * ==========================================================================
 * 分布式事务，本质上是对多个数据库的事务进行统一控制，按照控制力度可以分为：不控制、部分控制和完全控制。不控制就是不引入分布式事务，
 * 部分控制就是各种变种的两阶段提交，包括上面提到的消息事务+最终一致性、TCC模式，而完全控制就是完全实现两阶段提交。部分控制的好处是并
 * 发量和性能很好，缺点是数据一致性减弱了，完全控制则是牺牲了性能，保障了一致性，具体用哪种方式，最终还是取决于业务场景。作为技术人员，
 * 一定不能忘了技术是为业务服务的，不要为了技术而技术，针对不同业务进行技术选型也是一种很重要的能力！
 */

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlElement(name = "Id",required = true)
    private long id;

    @XmlElement(name = "ItemName",required = true)
    private String itemName;

    @XmlElement(name = "Price",required = true)
    private double price;

    @XmlElement(name = "BuyerName",required = true)
    private String buyerName;

    @XmlElement(name = "MailAddress",required = true)
    private String mailAddress;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
