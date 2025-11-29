ALTER TABLE wizard_change_request
  ALTER COLUMN payload TYPE text USING payload::text;