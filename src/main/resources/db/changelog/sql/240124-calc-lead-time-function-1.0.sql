CREATE OR REPLACE FUNCTION public.calc_lead_time()
	RETURNS trigger
	LANGUAGE plpgsql
AS $function$
begin
		new.lead_time := EXTRACT(EPOCH FROM new.resolved - new.created)/(60*60);
	    if new.start_process_update is not null then
        	new.cycle_time := EXTRACT(EPOCH FROM new.resolved - new.start_process_update)/(60*60);
end if;

	    if (new.to_test_init is not null) and (new.testing_init is not null) then
        	new.waiting_test_time := EXTRACT(EPOCH FROM new.testing_init - new.to_test_init)/(60*60);
end if;

	    if new.testing_init is not null then
        	new.testing_time := EXTRACT(EPOCH FROM new.resolved - new.testing_init)/(60*60);
end if;

RETURN NEW;
END;
$function$
;