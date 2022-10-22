package io.github.architers.common.module.tenant.domain;


/**
 * @author luyi
 */
public abstract class BaseTenantEntity extends BaseEntity {

    /**
     * 租户ID
     */
    protected Integer tenantId;

    public Integer getTenantId() {
        return tenantId;
    }

    public BaseTenantEntity setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
}
