CREATE  TABLE "hit"."Products" 
(
	"product_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"creation_date" DATETIME NOT NULL , 
	"barcode" TEXT NOT NULL  UNIQUE , 
	"shelf_life" INTEGER NOT NULL , 
	"three_month_supply" INTEGER NOT NULL , 
	"size_val" FLOAT NOT NULL , 
	"size_unit" TEXT NOT NULL , 
	"description" TEXT NOT NULL 
)

DROP TABLE "hit"."Products"

CREATE  TABLE "hit"."Items" 
(
	"item_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"barcode" VARCHAR(12) NOT NULL  UNIQUE , 
	"entry_date" DATETIME NOT NULL , 
	"exit_date" DATETIME, 
	"expiration_date" DATETIME NOT NULL , 
	"product_id" INTEGER NOT NULL , 
	"container_id" INTEGER NOT NULL 
)

DROP TABLE "hit"."Items"

CREATE  TABLE "hit"."ProductContainers" 
(
	"container_id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , 
	"name" TEXT NOT NULL , 
	"three_month_supply_value" FLOAT NOT NULL , 
	"three_month_supply_unit" TEXT NOT NULL , 
	"parent_id" INTEGER NOT NULL 
)

DROP TABLE "hit"."ProductContainers"

CREATE  TABLE "hit"."p_pc" 
(
	"p_pc_id" INTEGER PRIMARY KEY  NOT NULL  UNIQUE , 
	"product_id" INTEGER NOT NULL , 
	"container_id" INTEGER NOT NULL 
)

DROP TABLE "hit"."p_pc"
