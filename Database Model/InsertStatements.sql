--Purpose of this SQL queries is to load some default values
-- to the database for the project presentation.
--author: Michael Gulenko 4/22/2015

--CREATING USER
INSERT INTO Users (user_name, user_email, user_pwd, bridge_id) 
	   VALUES("Michael and Dallas","gulenko84@gmail.com","super pwd", NULL);
	   
	   
--CREATING A BRIDGE
INSERT INTO Bridges (factory_name, user_def_name)
		VALUES ("123456789", "Super Bridge");
		
--THEN ADDING BRIDGE TO THE USER
INSERT INTO Users_Bridges(user_id,bridge_id) VALUES (1,1);


--CREATING STATES
INSERT INTO States (state_type, frequency, state_interval) VALUES ("ON", NULL, NULL);
INSERT INTO States (state_type, frequency, state_interval) VALUES ("OFF", NULL, NULL);
INSERT INTO States (state_type, frequency, state_interval) VALUES ("BLINK", 3, 2);


--CREATING LIGHT BULBS IN OFF STATE
INSERT INTO Bulbs(factory_name, user_def_name, state_id, bridge_id, bulb_color, bulb_brightness, color_saturation)
	   VALUES ("1111","Leaving Room Ceiling", 2, 1, "Red", 20, 10);
INSERT INTO Bulbs(factory_name, user_def_name, state_id, bridge_id, bulb_color, bulb_brightness, color_saturation)
	   VALUES ("1111","Leaving Room Floor", 2, 1, "GREEN", 20, 10);
INSERT INTO Bulbs(factory_name, user_def_name, state_id, bridge_id, bulb_color, bulb_brightness, color_saturation)
	   VALUES ("1111","Leaving Room Stand", 2, 1, "BLUE", 50, 30);
INSERT INTO Bulbs(factory_name, user_def_name, state_id, bridge_id, bulb_color, bulb_brightness, color_saturation)
	   VALUES ("1111","Kitchen Ceiling 1", 2, 1, "PINK", 20, 10);
INSERT INTO Bulbs(factory_name, user_def_name, state_id, bridge_id, bulb_color, bulb_brightness, color_saturation)
	   VALUES ("1111","Kitchen Ceiling 2", 2, 1, "YELLOW", 20, 10);
	   
	   
--CREATING GROUPS
INSERT INTO Groups(group_name) VALUE ("Leaving Room");
INSERT INTO Groups(group_name) VALUE ("Kitchen");


--AND ASIGHNING LIGHT BULBS
INSERT INTO Bulbs_Groups(bulb_id, group_id, theme_id) VALUES (1,1,NULL);
INSERT INTO Bulbs_Groups(bulb_id, group_id, theme_id) VALUES (2,1,NULL);
INSERT INTO Bulbs_Groups(bulb_id, group_id, theme_id) VALUES (3,1,NULL);
INSERT INTO Bulbs_Groups(bulb_id, group_id, theme_id) VALUES (4,2,NULL);
INSERT INTO Bulbs_Groups(bulb_id, group_id, theme_id) VALUES (5,2,NULL);


--CREATING THEMES
INSERT INTO Themes (theme_name) VALUE ("RGB");
INSERT INTO Themes (theme_name) VALUE ("Sunset");
INSERT INTO Themes (theme_name) VALUE ("Relax");

--CREATING TRAITS FOR THEME RGB
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("RED",20,10,NULL,1);
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("GREEN",20,10,NULL,1);
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("BLUE",20,10,NULL,1);
	   
--CREATING TRAITS FOR THEME SUNSET
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("ORANGE",20,10, NULL,2);
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("PURPLE",20,10, NULL,2);
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("RED",20,10, NULL,2);

--CREATING TRAITS FOR THEME RELAX
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("GREEN",30,10, NULL,3);
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("GREEN",20,10, NULL,3);
INSERT INTO Traits (bulb_color, bulb_brightness, color_density, bulb_id, theme_id)
	   VALUES ("ORANGE",20,10, NULL,3);	   
	   