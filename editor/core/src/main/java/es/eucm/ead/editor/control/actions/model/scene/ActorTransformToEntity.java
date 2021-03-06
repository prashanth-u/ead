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
package es.eucm.ead.editor.control.actions.model.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import es.eucm.ead.editor.control.commands.CompositeCommand;
import es.eucm.ead.editor.control.commands.FieldCommand;
import es.eucm.ead.editor.model.Q;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.FieldName;

/**
 * Applies to a {@link ModelEntity} the transformation of an {@link Actor}
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd><strong>args[0]</strong> <em>{@link Actor}</em> An actor associated to an
 * entity. The entity will be accessed through {@link Q#getModelEntity(Actor)}</dd>
 * </dl>
 * {@link #perform(Object...)} will return {@code null} if no entity is
 * associated with the actor
 */
public class ActorTransformToEntity extends ActorTranslationToEntity {

	public ActorTransformToEntity() {
		super(true, false, Actor.class);
	}

	@Override
	public CompositeCommand perform(Object... args) {
		Actor actor = (Actor) args[0];
		ModelEntity modelEntity = Q.getModelEntity(actor);

		if (modelEntity == null) {
			return null;
		}

		CompositeCommand compositeCommand = super.perform(args);

		if (actor.getOriginX() != modelEntity.getOriginX()) {
			compositeCommand.addCommand(new FieldCommand(modelEntity,
					FieldName.ORIGIN_X, actor.getOriginX()));
		}

		if (actor.getOriginY() != modelEntity.getOriginY()) {
			compositeCommand.addCommand(new FieldCommand(modelEntity,
					FieldName.ORIGIN_Y, actor.getOriginY()));
		}

		if (actor.getRotation() != modelEntity.getRotation()) {
			compositeCommand.addCommand(new FieldCommand(modelEntity,
					FieldName.ROTATION, actor.getRotation()));
		}

		if (actor.getScaleX() != modelEntity.getScaleX()) {
			compositeCommand.addCommand(new FieldCommand(modelEntity,
					FieldName.SCALE_X, actor.getScaleX()));
		}

		if (actor.getScaleY() != modelEntity.getScaleY()) {
			compositeCommand.addCommand(new FieldCommand(modelEntity,
					FieldName.SCALE_Y, actor.getScaleY()));
		}

		return compositeCommand;
	}
}
