This project is an attempt to learn JavaFX and Scene Builder.  It is also another exercise in learning to use Github.

The main idea of the application is to provide a user interface that will record chemical lawn care information (granular and liquid fertilizer).  This project is not used in any production environment and was solely developed to explore new concepts and ideas using JavaFX.

Below is an image of the main screen.  On the main screen a user can select a daily production entry from the left table panel.  Users can add, remove, and edit production entries.

![Main Screen](https://github.com/drewbaumgartner/ProductionEntry/blob/master/Screenshots/mainScreen.png)

Editing an entry will bring up a window that provides textboxes and drop-down selections for the user to input their information.  The drop-down selections are populated from a series of text files.  

![Edit Entry](https://github.com/drewbaumgartner/ProductionEntry/blob/master/Screenshots/editEntry.png)

The users can edit these text files within the application by going to the Edit menu and selecting which field they want to edit.  In the image below, a user has selected the Edit menu option "Update Technicians".  Now they can edit the list of available technicians.  Once a user is done editing they simply click the "Done" button and it will save their list to the file.

![Update Techs](https://github.com/drewbaumgartner/ProductionEntry/blob/master/Screenshots/updateTechs.png)

The application saves all the production entries into a XML file that can be opened to populate the daily production entries.
