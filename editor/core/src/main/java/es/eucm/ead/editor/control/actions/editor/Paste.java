/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2014 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          CL Profesor Jose Garcia Santesmases 9,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.eucm.ead.editor.control.actions.editor;

import es.eucm.ead.editor.control.Clipboard.ClipboardListener;
import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.actions.EnabledOnLoadAction;
import es.eucm.ead.editor.model.events.LoadEvent;

/**
 * <p>
 * Pastes the content of the clipboard
 * </p>
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd>None</dd>
 * </dl>
 */
public class Paste extends EnabledOnLoadAction implements ClipboardListener {

	public Paste() {
		super(false, false);
	}

	@Override
	public void initialize(Controller controller) {
		super.initialize(controller);
		controller.getClipboard().addClipboardListener(this);
		// The paste action will be enable once a game is loaded
		// (checked at super.initialize)and if the clipboard is not empty
		setEnabled(checkAdditionalPreconditions() && isEnabled());
	}

	@Override
	public void perform(Object... args) {
		controller.getClipboard().paste();
	}

	@Override
	public void clipboardChanged(String clipboardContent) {
		setEnabled(true);
	}

	/**
	 * Override to check if the clipboard is empty when {@link LoadEvent}
	 */
	@Override
	public boolean checkAdditionalPreconditions() {
		return controller.getClipboard().getContents() != null;
	}

}
