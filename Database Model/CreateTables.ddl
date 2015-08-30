
CREATE TABLE Bridges
(
	bridge_id            INTEGER NOT NULL AUTO_INCREMENT,
	factory_name         CHAR(32) NOT NULL,
	user_def_name        CHAR(18) NULL,
	PRIMARY KEY 		 (bridge_id)
);

CREATE TABLE Users
(
	user_id              INTEGER NOT NULL AUTO_INCREMENT,
	user_name            CHAR(18) NOT NULL,
	user_email           CHAR(32) NOT NULL,
	user_pwd             CHAR(128) NOT NULL,
	bridge_id            INTEGER NULL,
	PRIMARY KEY 		 (user_id)
);

CREATE TABLE Users_Bridges
(
	user_id               INTEGER NOT NULL,
	bridge_id             INTEGER NOT NULL
);

CREATE TABLE Bulbs
(
	bulb_id              INTEGER NOT NULL AUTO_INCREMENT,
	factory_name         CHAR(32) NOT NULL,
	user_def_name        CHAR(32) NULL,
	state_id             INTEGER NOT NULL,
	bridge_id            INTEGER NOT NULL,
	bulb_color           CHAR(18) NULL,
	bulb_brightness      INTEGER NULL,
	color_saturation     CHAR(18) NULL,
	PRIMARY KEY 		 (bulb_id)
);

CREATE TABLE Groups
(
	group_id             INTEGER NOT NULL AUTO_INCREMENT,
	group_name           CHAR(32) NULL,
	PRIMARY KEY 	    (group_id)
);

CREATE TABLE States
(
	state_id             INTEGER NOT NULL AUTO_INCREMENT,
	state_type           CHAR(18) NOT NULL,
	frequency            INTEGER  NULL,
	state_interval       INTEGER NULL,
	PRIMARY KEY 		 (state_id)
);

CREATE TABLE Themes
(
	theme_id             INTEGER NOT NULL AUTO_INCREMENT,
	theme_name           CHAR(18) NULL,
	PRIMARY KEY 		 (theme_id)
);

CREATE TABLE Bulbs_Groups
(
	bulb_id              INTEGER NOT NULL,
	group_id             INTEGER NOT NULL,
	theme_id             INTEGER NULL
);

CREATE TABLE Triggers
(
	trigger_id           INTEGER NOT NULL AUTO_INCREMENT,
	trigger_type         CHAR(18) NOT NULL,
	group_id             INTEGER NOT NULL,
	theme_id             INTEGER NOT NULL,
	trigger_state        CHAR(18) NULL,
	state_id             INTEGER NOT NULL,
	x_coordinate         FLOAT NOT NULL,
	y_coordinate         FLOAT NOT NULL,
	fense_size           INTEGER NOT NULL,
	alarm_time           DATE NOT NULL,
	frequency            INTEGER NOT NULL,
	app_id               CHAR(64) NOT NULL,
	PRIMARY KEY 		 (trigger_id)
);

CREATE TABLE Traits
(
	trait_id             INTEGER NOT NULL AUTO_INCREMENT,
	bulb_color           CHAR(18) NOT NULL,
	bulb_brightness      INTEGER NOT NULL,
	color_density        INTEGER NOT NULL,
	bulb_id              INTEGER NULL,
	theme_id             INTEGER NULL,
	PRIMARY KEY 		 (trait_id)
);

ALTER TABLE Users
ADD FOREIGN KEY R_8 (bridge_id) REFERENCES Bridges (bridge_id);

ALTER TABLE Bulbs
ADD FOREIGN KEY R_29 (state_id) REFERENCES States (state_id);

ALTER TABLE Bulbs
ADD FOREIGN KEY R_31 (bridge_id) REFERENCES Bridges (bridge_id);

ALTER TABLE Bulbs_Groups
ADD FOREIGN KEY R_14 (bulb_id) REFERENCES Bulbs (bulb_id);

ALTER TABLE Bulbs_Groups
ADD FOREIGN KEY R_15 (group_id) REFERENCES Groups (group_id);

ALTER TABLE Bulbs_Groups
ADD FOREIGN KEY R_25 (theme_id) REFERENCES Themes (theme_id);

ALTER TABLE Triggers
ADD FOREIGN KEY R_26 (group_id) REFERENCES Groups (group_id);

ALTER TABLE Triggers
ADD FOREIGN KEY R_28 (theme_id) REFERENCES Themes (theme_id);

ALTER TABLE Triggers
ADD FOREIGN KEY R_30 (state_id) REFERENCES States (state_id);

ALTER TABLE Traits
ADD FOREIGN KEY R_32 (bulb_id) REFERENCES Bulbs (bulb_id);

ALTER TABLE Traits
ADD FOREIGN KEY R_33 (theme_id) REFERENCES Themes (theme_id);

ALTER TABLE Users_Bridges
ADD FOREIGN KEY R_35 (user_id) REFERENCES Users (user_id);

ALTER TABLE Users_Bridges
ADD FOREIGN KEY R_36 (bridge_id) REFERENCES Bridges (bridge_id);