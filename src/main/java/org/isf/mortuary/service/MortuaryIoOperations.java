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
package org.isf.mortuary.service;

import java.util.List;

import org.isf.generaldata.MessageBundle;
import org.isf.mortuary.model.Mortuary;
import org.isf.utils.db.TranslateOHServiceException;
import org.isf.utils.exception.OHServiceException;
import org.isf.utils.exception.model.OHExceptionMessage;
import org.isf.vactype.model.VaccineType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = OHServiceException.class)
@TranslateOHServiceException
public class MortuaryIoOperations {
	private MortuaryIoOperationRepository repository;

	public MortuaryIoOperations(MortuaryIoOperationRepository repository) {
		this.repository = repository;
	}

	/**
	 * Retrieves all stored {@link Mortuary}s
	 * @return
	 */
	public List<Mortuary> getMortuaries() throws OHServiceException {
		return repository.findAllMortuaries();
	}

	/**
	 * Updates the specified {@link Mortuary}.
	 *
	 * @param mortuary - the {@link Mortuary} to update.
	 * @return mortuary that has been updated.
	 * @throws OHServiceException if an error occurs during the update.
	 */
	public Mortuary updateMortuary(Mortuary mortuary) throws OHServiceException {
		return repository.save(mortuary);
	}

	/**
	 * Stores the specified {@link Mortuary}.
	 *
	 * @param mortuary the mortuary to store.
	 * @return mortuary that has been stored.
	 * @throws OHServiceException if an error occurs storing the mortuary.
	 */
	public Mortuary newMortuary(Mortuary mortuary) throws OHServiceException {
		return repository.save(mortuary);
	}

	/**
	 * Deletes a {@link Mortuary} in the DB.
	 *
	 * @param mortuary - the item to delete
	 * @throws OHServiceException
	 */
	public void deleteMortuary(Mortuary mortuary) throws OHServiceException {
		repository.delete(mortuary);
	}

	/**
	 * Checks if the code is already in use.
	 *
	 * @param code - the {@link Mortuary} code
	 * @return {@code true} if the code is already in use, {@code false} otherwise
	 * @throws OHServiceException
	 */
	public boolean isCodePresent(String code) throws OHServiceException {
		return repository.existsById(code);
	}

	/**
	 * Returns the {@link Mortuary} based on code
	 *
	 * @param code - the code, must not be {@literal null}
	 * @return the {@link Mortuary} or {@literal null} if none found
	 * @throws OHServiceException if {@code code} is {@literal null}
	 */
	public Mortuary findMortuaryByCode(String code) throws OHServiceException {
		if (code != null) {
			return repository.findById(code).orElse(null);
		}
		throw new OHServiceException(new OHExceptionMessage(MessageBundle.getMessage("angal.mortuary.codemostnotbenull.msg")));//"Mortuary code must not be null.";
	}
}
