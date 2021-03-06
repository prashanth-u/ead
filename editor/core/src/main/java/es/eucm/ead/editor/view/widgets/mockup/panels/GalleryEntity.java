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
package es.eucm.ead.editor.view.widgets.mockup.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.view.widgets.mockup.buttons.DescriptionCard;
import es.eucm.ead.editor.view.widgets.mockup.panels.GalleryGrid.SelectListener;
import es.eucm.ead.engine.I18N;
import es.eucm.ead.schema.editor.components.Note;
import es.eucm.ead.schema.editor.components.RepoElement;

/**
 * Represents a selectable entry for the {@link GalleryGrid} by implementing
 * SelectListener interface.
 */
public abstract class GalleryEntity extends DescriptionCard implements
		SelectListener {
	private static final float ANIMATION_DURATION = .4f;
	private boolean selected;
	private NinePatch selectedview;

	/**
	 * A widget displaying a game or a interactive element. (name, description,
	 * image...) Represents a selectable entry for the {@link GalleryGrid} by
	 * implementing SelectListener interface.
	 * 
	 * @param viewport
	 * @param i18n
	 * @param type
	 *            Used in case the title or description are null or empty to
	 *            display some alternative text.
	 * @param title
	 *            if is null or empty the type will be used to show an
	 *            informative text instead.
	 * @param description
	 *            if is null or empty the type will be used to show an
	 *            informative text instead.
	 * @param imageName
	 * @param skin
	 */
	public GalleryEntity(Note targetNote, Vector2 viewport, I18N i18n,
			String type, RepoElement repoElem, Skin skin,
			Controller controller, Class<?> action, Object... args) {
		super(targetNote, viewport, i18n, type, repoElem, skin, controller,
				action, args);
		selectedview = skin.getPatch("text_focused");
	}

	/**
	 * A widget displaying a game or an interactive element. (name, description,
	 * image...) Represents a selectable entry for the {@link GalleryGrid} by
	 * implementing SelectListener interface.
	 * 
	 * @param viewport
	 * @param i18n
	 * @param type
	 *            Used in case the title or description are null or empty to
	 *            display some alternative text.
	 * @param title
	 *            if is null or empty the type will be used to show an
	 *            informative text instead.
	 * @param description
	 *            if is null or empty the type will be used to show an
	 *            informative text instead.
	 * @param imageName
	 * @param skin
	 */
	public GalleryEntity(Note targetNote, Vector2 viewport, I18N i18n,
			String type, RepoElement repoElem, Skin skin, Controller controller) {
		super(targetNote, viewport, i18n, type, repoElem, skin, controller);
		selectedview = skin.getPatch("text_focused");
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (this.selected)
			selectedview.draw(batch, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void layout() {
		super.layout();

		float width = getWidth();
		float height = getHeight();
		setOrigin(width * .5f, height * .5f);
	}

	@Override
	public void select() {
		this.selected = true;
		setTransform(true);
		addAction(Actions.scaleTo(.9f, .9f, ANIMATION_DURATION,
				Interpolation.swingOut));
	}

	@Override
	public void deselect() {
		selected = false;
		addAction(Actions.sequence(Actions.scaleTo(1f, 1f, ANIMATION_DURATION,
				Interpolation.swingOut), Actions.run(onAnimationFinished)));
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	private final Runnable onAnimationFinished = new Runnable() {
		@Override
		public void run() {
			setTransform(false);
		}
	};
}
