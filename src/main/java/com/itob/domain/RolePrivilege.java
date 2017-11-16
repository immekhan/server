package com.itob.domain;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ROLE_PRIVILEGES")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RolePrivilege
    extends AbsAbstractAuditingEntity
{
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RolePrivilegePk id;

    @Deprecated
    public RolePrivilege() {}

    public RolePrivilegePk getId() {
        return id;
    }

    public void setId(RolePrivilegePk id) {
        this.id = id;
    }

    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append(super.getClass().getSimpleName());
        Serializable dummy = this.id;
        if (dummy == null) {
            buf.append(" [not yet persisted]");
        } else {
            buf.append(" #");
            buf.append(dummy);
        }
        return buf.toString();
    }

    public boolean equals(Object o)
    {
        if (o == null) {
            return false;
        }
        if (this.id == null) {
            return false;
        }
        if (!(super.getClass().isAssignableFrom(o.getClass()))) {
            return false;
        }

        RolePrivilege other = (RolePrivilege)o;

        return this.id.equals(other.getId());
    }

    public int hashCode()
    {
        return ((this.id == null) ? 0 : this.id.hashCode());
    }

    public String getRole()
    {
        return ((RolePrivilegePk)getId()).getRole();
    }

    public boolean isSetRole()
    {
        return ((RolePrivilegePk)getId()).isSetRole();
    }

    public String getPrivilege()
    {
        return ((RolePrivilegePk)getId()).getPrivilege();
    }

    public boolean isSetPrivilege()
    {
        return ((RolePrivilegePk)getId()).isSetPrivilege();
    }

    @Embeddable
    public static final class RolePrivilegePk
            implements Serializable
    {
        private static final long serialVersionUID = 1L;
        @Basic(optional=false)
        @Column(name="ID_ROLE", nullable=false)
        private String role;
        @Basic(optional=false)
        @Column(name="ID_PRIVILEGE", nullable=false)
        private String privilege;

        @Deprecated
        protected RolePrivilegePk() {}

        public RolePrivilegePk(String role, String privilege)
        {
            this.role = role;
            this.privilege = privilege;
        }

        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            RolePrivilegePk other = (RolePrivilegePk)obj;
            if (this.privilege == null)
            {
                if (other.privilege != null) {
                    return false;
                }
            }
            else if (!this.privilege.equals(other.privilege)) {
                return false;
            }
            if (this.role == null)
            {
                if (other.role != null) {
                    return false;
                }
            }
            else if (!this.role.equals(other.role)) {
                return false;
            }
            return true;
        }

        public int hashCode()
        {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.privilege == null ? 0 : this.privilege.hashCode());

            result = 31 * result + (this.role == null ? 0 : this.role.hashCode());
            return result;
        }

        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            sb.append('(');
            sb.append(this.role);
            sb.append(',');
            sb.append(this.privilege);
            sb.append(')');
            return sb.toString();
        }

        public String getRole()
        {
            return this.role;
        }

        public boolean isSetRole()
        {
            return this.role != null;
        }

        public String getPrivilege()
        {
            return this.privilege;
        }

        public boolean isSetPrivilege()
        {
            return this.privilege != null;
        }
    }
}

