-- :name realtimestats :? :*
-- :doc RealTime ICD Statistics
SELECT *
FROM RtICDStatistics

-- :name loggedinagents :? :*
-- :doc  all loggedinagents from RT stats
SELECT loggedinagents AS agents
FROM RtICDStatistics

-- :name csqlist :? :*
-- :doc UCCX CSQ list
select CSQNAME from contactservicequeue where active = 'T' and queuetype = 0

-- :name realtime-queues :? :*
-- :doc select all data from RealTime CSQ Summary
select *,(callshandled + callsdequeued) as callshandeq,(callsabandoned - callsdequeued) as callsabdeq
FROM RtCSQsSummary
