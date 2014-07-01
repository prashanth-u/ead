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
package es.eucm.ead.editor.control;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import es.eucm.ead.editor.assets.ApplicationAssets;
import es.eucm.ead.editor.assets.EditorGameAssets;
import es.eucm.ead.editor.control.actions.ArgumentsValidationException;
import es.eucm.ead.editor.control.actions.EditorActionException;
import es.eucm.ead.editor.control.appdata.ReleaseInfo;
import es.eucm.ead.editor.control.background.BackgroundExecutor;
import es.eucm.ead.editor.control.commands.Command;
import es.eucm.ead.editor.control.engine.Engine;
import es.eucm.ead.editor.control.pastelisteners.ModelEntityCopyListener;
import es.eucm.ead.editor.model.Model;
import es.eucm.ead.editor.platform.Platform;
import es.eucm.ead.editor.view.builders.ViewBuilder;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.network.requests.RequestHelper;

/**
 * Mediator and main controller of the editor's functionality
 * 
 */
public class Controller {

	/**
	 * Singletion shaperenderer, shared across the editor
	 */
	private ShapeRenderer shapeRenderer;

	/**
	 * Game model managed by the editor.
	 */
	private Model model;

	/**
	 * Platform dependent functionality
	 */
	private Platform platform;

	/**
	 * Asset manager used for internal's editor assets.
	 */
	private ApplicationAssets applicationAssets;

	/**
	 * Asset manager for the current openend game's project.
	 */
	private EditorGameAssets editorGameAssets;

	protected Views views;

	private Actions actions;

	/**
	 * Manages editor preferences
	 */
	private Preferences preferences;

	/**
	 * Manage editor's command history.
	 */
	private Commands commands;

	/**
	 * Object for dealing with http connections
	 */
	private RequestHelper requestHelper;

	/**
	 * Manage keyboard mappings to editor's functionality
	 */
	private ShortcutsMap shortcutsMap;

	private Clipboard clipboard;

	/**
	 * Info about the version and release type of the application. Used for
	 * checking updates
	 */
	private ReleaseInfo releaseInfo;

	private Tracker tracker;

	private Templates templates;

	private BackgroundExecutor backgroundExecutor;

	private Engine engine;

	public Controller(Platform platform, Files files, Group viewsContainer,
			Group modalsContainer) {
		this.shapeRenderer = new ShapeRenderer();
		this.platform = platform;
		this.requestHelper = platform.getRequestHelper();
		this.applicationAssets = createApplicationAssets(files);
		this.editorGameAssets = new EditorGameAssets(files);
		this.templates = new Templates(this);
		this.model = new Model();
		this.commands = new Commands(model);
		this.views = createViews(viewsContainer, modalsContainer);
		this.clipboard = new Clipboard(Gdx.app.getClipboard(), model,
				editorGameAssets);
		this.actions = new Actions(this);
		this.backgroundExecutor = new BackgroundExecutor();
		this.preferences = applicationAssets.loadPreferences();
		// Get the release info from editor assets
		this.releaseInfo = applicationAssets.loadReleaseInfo();
		this.shortcutsMap = new ShortcutsMap(this);
		this.engine = new Engine(this);
		setTracker();
		setClipboard();
		loadPreferences();
	}

	protected ApplicationAssets createApplicationAssets(Files files) {
		return new ApplicationAssets(files);
	}

	protected Views createViews(Group viewsContainer, Group modalsContainer) {
		return new Views(this, viewsContainer, modalsContainer);
	}

	private void setClipboard() {
		clipboard.registerCopyListener(ModelEntity.class,
				new ModelEntityCopyListener(this));
	}

	private void setTracker() {
		// FIXME obtain from platform the actual tracker implementation
		this.tracker = new Tracker(releaseInfo.getBugReportURL(), this);
		tracker.setEnabled(preferences.getBoolean(Preferences.TRACKING_ENABLED,
				false));
		tracker.startSession();
	}

	/**
	 * Process preferences concerning the controller
	 */
	private void loadPreferences() {
		getApplicationAssets().getI18N().setLang(
				preferences.getString(Preferences.EDITOR_LANGUAGE));
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public Model getModel() {
		return model;
	}

	public EditorGameAssets getEditorGameAssets() {
		return editorGameAssets;
	}

	public ApplicationAssets getApplicationAssets() {
		return applicationAssets;
	}

	public Platform getPlatform() {
		return platform;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public Commands getCommands() {
		return commands;
	}

	public Actions getActions() {
		return actions;
	}

	public Views getViews() {
		return views;
	}

	public <T extends ViewBuilder> void view(Class<T> view) {
		views.setView(view);
	}

	public ShortcutsMap getShortcutsMap() {
		return shortcutsMap;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public RequestHelper getRequestHelper() {
		return requestHelper;
	}

	public Tracker getTracker() {
		return tracker;
	}

	public Templates getTemplates() {
		return templates;
	}

	public BackgroundExecutor getBackgroundExecutor() {
		return backgroundExecutor;
	}

	public Engine getEngine() {
		return engine;
	}

	/**
	 * Executes an editor action with the given name and arguments
	 * 
	 * @param actionClass
	 *            the action class
	 * @param args
	 *            the arguments for the action
	 */
	public void action(Class actionClass, Object... args) {
		try {
			Gdx.app.debug("Controller",
					ClassReflection.getSimpleName(actionClass)
							+ prettyPrintArgs(args));
			actions.perform(actionClass, args);
			// FIXME correct this when actions serialization is ready
			tracker.actionPerformed(actionClass.toString());
		} catch (ClassCastException e) {
			throw new EditorActionException(
					"Something went wrong when executing action "
							+ actionClass
							+ " with arguments "
							+ prettyPrintArgs(args)
							+ ". Perhaps the number of arguments is not correct or these are not valid",
					e);
		} catch (NullPointerException e) {
			throw new EditorActionException(
					"Something went wrong when executing action "
							+ actionClass
							+ " with arguments "
							+ prettyPrintArgs(args)
							+ ". Perhaps the number of arguments is not correct or these are not valid",
					e);
		} catch (ArgumentsValidationException e) {
			Gdx.app.error("Controller", "Invalid arguments exception for "
					+ actionClass);
		}
	}

	/**
	 * Just formats an array of objects for console printing. For debugging only
	 */
	private String prettyPrintArgs(Object... args) {
		String str = "[";
		for (Object arg : args) {
			str += (arg instanceof String ? "\"" : "")
					+ (arg == null ? "null" : arg.toString())
					+ (arg instanceof String ? "\"" : "") + " , ";
		}
		if (args.length > 0) {
			str = str.substring(0, str.length() - 3);
		}
		str += "]";
		return str;
	}

	/**
	 * Executes a command, an takes care of notifying to all model listeners all
	 * the changes performed by it
	 * 
	 * @param command
	 *            the command
	 */
	public void command(Command command) {
		commands.command(command);
	}

	public String getLoadingPath() {
		return editorGameAssets.getLoadingPath();
	}

	public void setLanguage(String language) {
		getApplicationAssets().getI18N().setLang(language);
		views.reinitializeAllViews();
		preferences.putString(Preferences.EDITOR_LANGUAGE, language);
	}

	/**
	 * @return The object with all information related to the current
	 *         installation
	 */
	public ReleaseInfo getReleaseInfo() {
		return releaseInfo;
	}

	/**
	 * Returns the version of the application (e.g. 2.0.0). Needed for setting
	 * {@link es.eucm.ead.schema.editor.components.Versions#appVersion} when the
	 * game is created and saved.
	 * 
	 * See {@link es.eucm.ead.editor.assets.ApplicationAssets#loadReleaseInfo()}
	 * and ReleaseInfoTest for more details
	 * 
	 * @return The version number of the application (e.g. "2.0.0").
	 */
	public String getAppVersion() {
		return releaseInfo.getAppVersion();
	}

	/**
	 * Returns the path that points to the engine-with-dependencies.jar file
	 * used to export games as jar files. It is read from release.json. Can be a
	 * relative or absolute path
	 * 
	 * @return The path to the engine jar (e.g. "libs/engine.jar")
	 */
	public String getEngineLibPath() {
		return releaseInfo.getEngineLibPath();
	}

	/**
	 * The editor is exiting. Perform all operations before finalizing the
	 * editor completely
	 */
	public void exit() {
		applicationAssets.dispose();
		editorGameAssets.dispose();
		shapeRenderer.dispose();
		tracker.endSession();
		preferences.flush();
	}

	/**
	 * The controller checks and updates pending tasks (e.g., state of
	 * background tasks)
	 */
	public void act(float delta) {
		editorGameAssets.update();
		applicationAssets.update();
		backgroundExecutor.act();
		engine.update(delta);
	}

	public static interface BackListener {
		/**
		 * Called when the Back key was pressed in Android.
		 */
		void onBackPressed();
	}
}
