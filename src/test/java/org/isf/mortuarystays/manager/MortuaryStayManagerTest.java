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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.assertj.core.api.Condition;
import org.isf.OHCoreTestCase;
import org.isf.mortuarystays.model.MortuaryStay;
import org.isf.mortuarystays.service.MortuaryStayIoOperationRepository;
import org.isf.mortuarystays.service.MortuaryStayIoOperations;
import org.isf.utils.exception.OHDataIntegrityViolationException;
import org.isf.utils.exception.OHDataValidationException;
import org.isf.utils.exception.OHException;
import org.isf.utils.exception.OHServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MortuaryStayManagerTest extends OHCoreTestCase {

	private static TestMortuaryStay testMortuaryStay;

	@Autowired
	MortuaryStayIoOperations mortuaryStayIoOperations;

	@Autowired
	MortuaryStayIoOperationRepository mortuaryStayIoOperationRepository;

	@Autowired
	MortuaryStayManager mortuaryStayBrowserManager;

	@BeforeAll
	static void setUpClass() {
		testMortuaryStay = new TestMortuaryStay();
	}

	@BeforeEach
	void setUp() {
		cleanH2InMemoryDb();
	}

	@Test
	@DisplayName("Get all mortuaries stays")
	void testGetAll() throws Exception {
		String code = setupTestMortuaryStays(false);
		MortuaryStay foundMortuary = mortuaryStayBrowserManager.getByCode(code);
		assertThat(foundMortuary).isNotNull();
		List<MortuaryStay> mortuaries = mortuaryStayBrowserManager.getAll();
		assertThat(mortuaries.size()).isEqualTo(1);
	}

	@Test
	void testUpdate() throws Exception {
		String code = setupTestMortuaryStays(false);
		MortuaryStay foundMortuary = mortuaryStayBrowserManager.getByCode(code);
		assertThat(foundMortuary).isNotNull();
		foundMortuary.setDescription("Update");
		MortuaryStay updatedMortuary = mortuaryStayBrowserManager.update(foundMortuary);
		assertThat(updatedMortuary).isNotNull();
		assertThat(updatedMortuary.getDescription()).isEqualTo("Update");
	}

	@Test
	void testAdd() throws Exception {
		MortuaryStay mortuary = testMortuaryStay.setup(true);
		assertThat(mortuaryStayBrowserManager.add(mortuary)).isNotNull();
		checkMortuaryIntoDb(mortuary.getCode());
	}

	@Test
	void testIsCodePresent() throws Exception {
		String code = setupTestMortuaryStays(false);
		assertThat(mortuaryStayBrowserManager.isCodePresent(code)).isTrue();
	}

	@Test
	void testDelete() throws Exception {
		String code = setupTestMortuaryStays(false);
		MortuaryStay foundMortuary = mortuaryStayIoOperations.getByCode(code);
		assertThat(foundMortuary).isNotNull();
		mortuaryStayBrowserManager.delete(foundMortuary);
		assertThat(mortuaryStayBrowserManager.isCodePresent(code)).isFalse();
	}

	@Test
	void testGetByCode() throws Exception {
		String code = setupTestMortuaryStays(false);
		MortuaryStay foundMortuary = mortuaryStayBrowserManager.getByCode(code);
		assertThat(foundMortuary).isNotNull();
		assertThat(foundMortuary.getCode()).isEqualTo(code);
	}

	@Test
	void testMgrFindMortuaryWithNullThrowException() {
		assertThatThrownBy(() -> mortuaryStayBrowserManager.getByCode(null))
			.isInstanceOf(OHDataValidationException.class)
			.has(
				new Condition<Throwable>(
					e -> ((OHServiceException) e).getMessages().size() == 1, "Expecting single validation error")
			);
	}

	@Test
	void testMgrValidationCodeWithTooLongException() throws Exception {
		MortuaryStay mortuary = testMortuaryStay.setup(false);
		mortuary.setCode("thisIsACodeThatIsTooLong");
		assertThatThrownBy(() -> mortuaryStayBrowserManager.add(mortuary))
			.isInstanceOf(OHDataValidationException.class)
			.has(
				new Condition<Throwable>(
					e -> ((OHServiceException) e).getMessages().size() == 1, "Expecting single validation error")
			);
	}

	@Test
	void testMgrValidationDescriptionWithEmptyException() throws Exception {
		String code = setupTestMortuaryStays(true);
		MortuaryStay mortuary = mortuaryStayBrowserManager.getByCode(code);
		mortuary.setDescription("");
		assertThatThrownBy(() -> mortuaryStayBrowserManager.add(mortuary))
			.isInstanceOf(OHDataIntegrityViolationException.class)
			.has(
				new Condition<Throwable>(
					e -> ((OHServiceException) e).getMessages().size() == 1, "Expecting single validation error")
			);
	}

	@Test
	void testMgrValidationCodeExistsException() throws Exception {
		String code = setupTestMortuaryStays(true);
		MortuaryStay mortuary = mortuaryStayBrowserManager.getByCode(code);
		assertThatThrownBy(() -> mortuaryStayBrowserManager.add(mortuary))
			.isInstanceOf(OHDataIntegrityViolationException.class)
			.has(
				new Condition<Throwable>(
					e -> ((OHServiceException) e).getMessages().size() == 1, "Expecting single validation error")
			);
	}

	private String setupTestMortuaryStays(boolean usingSet) throws OHException {
		MortuaryStay mortuary = testMortuaryStay.setup(usingSet);
		mortuaryStayIoOperationRepository.save(mortuary);
		return mortuary.getCode();
	}

	private void checkMortuaryIntoDb(String code) throws OHServiceException {
		MortuaryStay foundMortuary = mortuaryStayBrowserManager.getByCode(code);
		testMortuaryStay.check(foundMortuary);
	}
}