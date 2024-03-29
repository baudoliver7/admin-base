/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.base.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.base.api.Access;
import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.ProfileAccesses;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.cactoos.text.Joined;

/**
 * All profile accesses from Database.
 *
 * @since 0.1
 */
public final class DbProfileAccesses implements ProfileAccesses {

	/**
	 * DataSource.
	 */
	private final DataSource source;

	/**
	 * Profile.
	 */
	private final Profile profile;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 * @param profile Profile
	 */
	public DbProfileAccesses(final DataSource source, final Profile profile) {
		this.source = source;
		this.profile = profile;
	}

	private final boolean isAdmin() {
		return this.profile.name().equals("Administrateur");
	}

	@Override
	public Iterable<Access> iterate() {
		try {
			if(this.isAdmin()) {
				return () -> Access.VALUES.stream().filter(
					a -> !a.code().equals("TRAVAILLER_DANS_SON_PROPRE_ESPACE_DE_TRAVAIL")
				).iterator();
			} else {
				return
	                new JdbcSession(this.source)
	                    .sql(
	                        new Joined(
	                            " ",
	                            "SELECT access_id FROM ad_access_profile",
	            				"WHERE profile_id=?"
	                        ).toString()
	                    )
	                    .set(this.profile.id())
	                    .select(
	                		new ListOutcome<>(
	                        	rset -> Access.get(rset.getString(1))
	                        )
	                    );
			}
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
	}

	@Override
	public boolean has(final Access access) {
		try {
			if(this.isAdmin() && !access.code().equals("TRAVAILLER_DANS_SON_PROPRE_ESPACE_DE_TRAVAIL")) {
				return true;
			} else {
				return new JdbcSession(this.source)
				        .sql(
			        		new Joined(
		        				" ",
		        				"SELECT COUNT(*) FROM ad_access_profile",
		        				"WHERE access_id=? AND profile_id=?"
		        			).toString()
		        		)
				        .set(access.code())
				        .set(this.profile.id())
				        .select(new SingleOutcome<>(Long.class)) > 0;
			}
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	@Override
	public void add(final Access access) {
		try {
			if(this.isAdmin()) {
				return;
			}
			new JdbcSession(this.source)
            .sql(
                new Joined(
                    " ",
                    "INSERT INTO ad_access_profile",
                    "(access_id, profile_id)",
                    "VALUES",
                    "(?, ?)"
                ).toString()
            )
            .set(access.code())
            .set(this.profile.id())
            .insert(Outcome.VOID);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}

	@Override
	public void remove(final Access access) {
		try {
			if(this.isAdmin()) {
				return;
			}
            new JdbcSession(this.source)
                .sql(
                    new Joined(
                        " ",
                        "DELETE FROM ad_access_profile",
                        "WHERE access_id=? AND profile_id=?"
                    ).toString()
                )
                .set(access.code())
                .set(this.profile.id())
                .execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}
	
	@Override
	public void removeAll() {
		try {
			if(this.isAdmin()) {
				return;
			}
            new JdbcSession(this.source)
                .sql(
                    new Joined(
                        " ",
                        "DELETE FROM ad_access_profile",
                        "WHERE profile_id=?"
                    ).toString()
                )
                .set(this.profile.id())
                .execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}

}
