alter table olymp_organizations
drop column id;

alter table olymp_organizations
    add primary key(olympiad_id, organization_id);


alter table news_translation
    add primary key (news_id, language_code);

alter table olymp_organizations
    ALTER COLUMN olympiad_id TYPE BIGINT