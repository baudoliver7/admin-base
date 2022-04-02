/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.base.db.jooq.generated;


import io.surati.gap.admin.base.db.jooq.generated.tables.AdAccessProfile;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdEventLog;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdPerson;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdProfile;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdUser;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.ad_access_profile</code>.
     */
    public final AdAccessProfile AD_ACCESS_PROFILE = AdAccessProfile.AD_ACCESS_PROFILE;

    /**
     * The table <code>public.ad_event_log</code>.
     */
    public final AdEventLog AD_EVENT_LOG = AdEventLog.AD_EVENT_LOG;

    /**
     * The table <code>public.ad_person</code>.
     */
    public final AdPerson AD_PERSON = AdPerson.AD_PERSON;

    /**
     * The table <code>public.ad_profile</code>.
     */
    public final AdProfile AD_PROFILE = AdProfile.AD_PROFILE;

    /**
     * The table <code>public.ad_user</code>.
     */
    public final AdUser AD_USER = AdUser.AD_USER;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.AD_EVENT_LOG_ID_SEQ,
            Sequences.AD_PERSON_ID_SEQ,
            Sequences.AD_PROFILE_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            AdAccessProfile.AD_ACCESS_PROFILE,
            AdEventLog.AD_EVENT_LOG,
            AdPerson.AD_PERSON,
            AdProfile.AD_PROFILE,
            AdUser.AD_USER);
    }
}