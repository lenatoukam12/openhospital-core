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
package org.isf.mortuarystays.manager;

import org.isf.mortuarystays.model.MortuaryStay;
import org.isf.utils.exception.OHException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMortuaryStay {
	private String code = "l";
	private String name = "Long stay";
	private String description = "Stays in long time";
	private int daysMax = 8;
	private int daysMin = 3;

	public MortuaryStay setup(boolean usingSet) throws OHException {
		MortuaryStay mortuary;

		if (usingSet) {
			mortuary = new MortuaryStay();
			setParameters(mortuary);
		} else{
			mortuary = new MortuaryStay(code, name,description, daysMax, daysMin);
		}
		return mortuary;
	}

	public void setParameters(MortuaryStay mortuary) {
		mortuary.setCode(code);
		mortuary.setName(name);
		mortuary.setDescription(description);
		mortuary.setMaxDays(daysMax);
		mortuary.setMinDays(daysMin);
	}

	public void check(MortuaryStay mortuary) {
		assertThat(mortuary.getCode()).isEqualTo(code);
		assertThat(mortuary.getName()).isEqualTo(name);
		assertThat(mortuary.getDescription()).isEqualTo(description);
		assertThat(mortuary.getMaxDays()).isEqualTo(daysMax);
		assertThat(mortuary.getMinDays()).isEqualTo(daysMin);
	}
}