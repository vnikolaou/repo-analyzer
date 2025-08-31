-- -------------------------------------------------------------
-- TablePlus 6.4.8(608)
--
-- https://tableplus.com/
--
-- Database: repodb
-- Generation Time: 2025-08-30 20:05:54.6920
-- -------------------------------------------------------------


DROP TABLE IF EXISTS "public"."setting";
-- Table Definition
CREATE TABLE "public"."setting" (
    "key" varchar(255) NOT NULL,
    "value" varchar(255) NOT NULL,
    PRIMARY KEY ("key")
);

DROP TABLE IF EXISTS "public"."run_item";
-- Table Definition
CREATE TABLE "public"."run_item" (
    "id" int8 NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT now(),
    "max_limit" int8 NOT NULL,
    "name" varchar(255) NOT NULL,
    "search_query" varchar(255) NOT NULL,
    "status" varchar(255) NOT NULL,
    "total_results" int8 NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."repository";
-- Table Definition
CREATE TABLE "public"."repository" (
    "id" int8 NOT NULL,
    "analyzed" bool,
    "chosen" bool,
    "clone_url" varchar(255),
    "cloned" bool,
    "comments" varchar(255),
    "commite_id" varchar(255),
    "commits" int4,
    "complexity" int4,
    "contributors" int4,
    "coverage" numeric(5,2),
    "created_at" timestamptz,
    "failed" bool,
    "forks" int4,
    "full_name" varchar(255),
    "has_issues" bool,
    "private" bool,
    "license" varchar(255),
    "open_issues" int4,
    "pushed_at" timestamptz,
    "repo_id" varchar(255),
    "size" int4,
    "ssh_url" varchar(255),
    "stars" int4,
    "updated_at" timestamptz,
    "watchers" int4,
    "run_item_id" int8 NOT NULL,
    PRIMARY KEY ("id")
);


ALTER TABLE "public"."repository" ADD FOREIGN KEY ("run_item_id") REFERENCES "public"."run_item"("id");
