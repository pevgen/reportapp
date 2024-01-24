CREATE TRIGGER calc_lead_time_trg BEFORE INSERT OR UPDATE ON public.issues
    FOR EACH ROW EXECUTE FUNCTION public.calc_lead_time();