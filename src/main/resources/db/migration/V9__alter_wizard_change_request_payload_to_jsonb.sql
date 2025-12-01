ALTER TABLE wizard_change_request
  ALTER COLUMN payload TYPE jsonb USING payload::jsonb;