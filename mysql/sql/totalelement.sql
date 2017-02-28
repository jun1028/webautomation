use page;
drop table if exists totalelement;
create  table page.totalelement (id int AUTO_INCREMENT primary key not null,des VARCHAR(250) NULL  ,idvalue VARCHAR(150) NULL, namevalue  VARCHAR(150) , xpathvalue VARCHAR(250) ,cssvalue VARCHAR(250) ,pagename VARCHAR(45) default "index", keyvalue VARCHAR(200), UNIQUE INDEX `keyvalue_UNIQUE` (`keyvalue` ASC)); 
