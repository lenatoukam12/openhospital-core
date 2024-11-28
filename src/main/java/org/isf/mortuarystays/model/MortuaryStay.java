/*
 * Open Hospital (www.open-hospital.org)
 * Copyright © 2006-2024 Informatici Senza Frontiere (info@informaticisenzafrontiere.org)
 *
 * Open Hospital is a free and open source software for healthcare data management.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.isf.mortuarystays.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.isf.utils.db.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "OH_MORTUARYSTAYS")
@EntityListeners(AuditingEntityListener.class)
@AttributeOverride(name = "createdBy", column = @Column(name = "MRTST_CREATED_BY", updatable = false))
@AttributeOverride(name = "createdDate", column = @Column(name = "MRTST_CREATED_DATE", updatable = false))
@AttributeOverride(name = "lastModifiedBy", column = @Column(name = "MRTST_LAST_MODIFIED_BY"))
@AttributeOverride(name = "active", column = @Column(name = "MRTST_ACTIVE"))
@AttributeOverride(name = "lastModifiedDate", column = @Column(name = "MRTST_LAST_MODIFIED_DATE"))
public class MortuaryStay extends Auditable<String> {

	@Id
	@Column(name = "MRTST_CODE")
	private String code;

	@NotNull
	@Column(name = "MRTST_NAME")
	private String name;

	@NotNull
	@Column(name = "MRTST_DESC")
	private String description;

	@NotNull
	@Column(name = "MRTST_MAX_DAYS")
	private int maxDays;

	@NotNull
	@Column(name = "MRTST_MIN_DAYS")
	private int minDays;

	public MortuaryStay() {
		super();
	}

	public MortuaryStay(String code, String name, String description, int maxDays, int minDays) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.maxDays = maxDays;
		this.minDays = minDays;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMaxDays() {
		return maxDays;
	}
	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}
	public int getMinDays() {
		return minDays;
	}
	public void setMinDays(int minDays) {
		this.minDays = minDays;
	}
	public String toString() {
		return "mortuarystays code=." + getCode() + ". description=." + getDescription() + ". name=." + getName() + '.';
	}
}
