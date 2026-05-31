# A/B Test Plan: TaskVerse Smart Task Match

**This task is part of Community Dreams Foundation (CDF) through the CDF Portal / TaskVerse.**

## 1. Summary

This document defines a comprehensive A/B test plan for a fictional TaskVerse feature called **Smart Task Match**. The feature recommends high-fit volunteer tasks based on a user's stated interests, prior task history, and estimated weekly availability.

The goal of the experiment is to determine whether adding a personalized recommendation panel increases the rate at which eligible volunteers claim a task.

## 2. Reference Guide / Template Used

The plan structure is based on public A/B testing guidance from:

- VWO A/B Testing Guide: https://vwo.com/ab-testing/
- VWO Sample Size Guide: https://vwo.com/glossary/sample-size/
- Optimizely Sample Size Calculations for Experiments: https://www.optimizely.com/insights/blog/sample-size-calculations-for-experiments/

These references emphasize defining a hypothesis before launch, selecting measurable success metrics, planning sample size and duration, and using experiment results to make rollout decisions.

## 3. Feature Under Test

**Product:** TaskVerse volunteer task portal  
**Feature:** Smart Task Match recommendation panel  
**Target surface:** Volunteer dashboard and task discovery page  
**Target users:** Logged-in volunteers who have not claimed a task in the last 14 days

### Feature Description

Smart Task Match displays a personalized panel titled **"Recommended for You"**. It surfaces three task cards ranked by estimated fit. Each card includes:

- Task title
- Estimated hours
- Due date
- Skill or interest match reason
- Primary call-to-action: **View Task**

## 4. Hypothesis

If TaskVerse shows inactive volunteers a personalized **Smart Task Match** panel, then more eligible users will claim a task because the portal reduces browsing effort and highlights relevant opportunities.

**Expected outcome:** The variant will increase the task claim conversion rate from **12.0%** to at least **13.8%**, a **15% relative lift**.

**Primary metric impacted:** Task claim conversion rate.

## 5. Experiment Design

### Population

Eligible users are logged-in volunteers who:

- Have an active TaskVerse account
- Have visited the dashboard or task discovery page
- Have not claimed a task in the previous 14 days
- Are not internal admins, staff testers, or automated test accounts

### Randomization

Eligible users will be randomly assigned at the user level.

- 50% Control
- 50% Variant

Assignment should persist for the full test duration to prevent users from switching treatments across sessions.

### Control: Version A

Users see the current dashboard and task discovery experience:

- Standard task list
- Search and filter controls
- No personalized recommendation panel

### Variant: Version B

Users see the current experience plus a Smart Task Match panel above the standard task list:

- Three recommended tasks
- Match reason on each card
- Estimated hours and due date
- **View Task** CTA on each recommended task

## 6. Success Metrics

### Primary Metric

**Task claim conversion rate**

Definition:

Number of eligible users who claim at least one task within 7 days of exposure divided by total eligible exposed users.

Formula:

`task_claim_conversion_rate = users_with_task_claim / eligible_exposed_users`

### Secondary Metrics

1. **Task detail view rate**
   - Measures whether recommendations increase task exploration.
   - Formula: users who view a task detail page / eligible exposed users

2. **Time to first task claim**
   - Measures whether recommendations reduce decision time.
   - Formula: median time from first exposure to task claim

3. **Task completion rate**
   - Measures downstream quality.
   - Formula: claimed tasks completed / claimed tasks

4. **Task unclaim rate**
   - Guardrail metric to detect poor recommendation fit.
   - Formula: unclaimed tasks / claimed tasks

5. **Support or confusion signals**
   - Guardrail metric based on support tickets, help clicks, or feedback mentioning recommendations.

## 7. Sample Size and Duration

### Assumptions

- Baseline task claim conversion rate: **12.0%**
- Minimum detectable effect: **15% relative lift**
- Target variant conversion rate: **13.8%**
- Confidence level: **95%**
- Statistical power: **80%**
- Allocation: **50/50 split**
- Estimated eligible traffic: **1,400 users per day**
- Estimated traffic per variant: **700 users per day**

### Rough Sample Size Estimate

Using a two-proportion test approximation:

- Required sample per variant: approximately **5,437 users**
- Total required sample: approximately **10,874 users**

### Estimated Duration

At roughly 700 eligible users per variant per day:

- Minimum duration to reach sample size: approximately **8 days**
- Planned test duration: **14 days**

The experiment should run for two full weeks to capture weekday/weekend behavior and reduce bias from weekly volunteer activity patterns.

## 8. Data Collection Plan

### Required Events

1. `experiment_exposed`
   - Properties: user_id, variant, timestamp, page, experiment_id

2. `recommendation_panel_viewed`
   - Properties: user_id, variant, timestamp, recommended_task_ids

3. `recommended_task_clicked`
   - Properties: user_id, task_id, rank, match_reason, timestamp

4. `task_detail_viewed`
   - Properties: user_id, task_id, source, timestamp

5. `task_claimed`
   - Properties: user_id, task_id, source, timestamp

6. `task_unclaimed`
   - Properties: user_id, task_id, timestamp

7. `task_completed`
   - Properties: user_id, task_id, completion_timestamp

## 9. Risks and Mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| Recommendations are not relevant | Users may ignore the panel or claim mismatched tasks | Include match reasons and monitor unclaim rate |
| Variant increases claims but lowers completions | More claims may not mean better volunteer outcomes | Use completion rate and unclaim rate as guardrails |
| Repeat visitors see inconsistent experiences | Experiment contamination may reduce trust and data quality | Persist assignment at user level |
| Seasonal or weekly traffic patterns bias results | Results may not generalize | Run for at least two full weeks |
| Low traffic delays conclusion | Test may not reach sample size quickly | Extend test up to 21 days if traffic is lower than expected |

## 10. Decision Rules

### Ship / Roll Out

Roll out Smart Task Match if:

- Primary metric improves by at least the minimum detectable effect or reaches statistical significance
- Task completion rate does not decrease meaningfully
- Task unclaim rate does not increase by more than 5% relative
- No major usability or support issues appear

### Iterate

Revise and retest if:

- Task detail views increase but task claims do not
- Users click recommendations but do not claim tasks
- Qualitative feedback suggests the recommendations are unclear

Potential iteration ideas:

- Improve match reasons
- Add filters for availability or skills
- Adjust recommendation ranking logic
- Test a different CTA such as **Claim Task** instead of **View Task**

### Do Not Roll Out

Do not roll out if:

- Task claim conversion decreases
- Task completion rate drops meaningfully
- Task unclaim rate increases beyond the guardrail threshold
- Users report confusion or distrust in the recommendations

## 11. Rollout Strategy

### If Successful

1. Roll out to 25% of eligible users for 3 days.
2. Monitor task claim rate, completion rate, unclaim rate, page performance, and support signals.
3. Increase to 50% for another 3 days if guardrails remain healthy.
4. Roll out to 100% of eligible users.
5. Continue monitoring for 14 days after full release.

### If Inconclusive

1. Check whether the experiment reached the required sample size.
2. Extend the test up to 21 days if traffic is below expectation.
3. If still inconclusive, review secondary metrics and qualitative feedback.
4. Prioritize a stronger variant if evidence suggests user interest but insufficient conversion lift.

### If Unsuccessful

1. Keep the control experience.
2. Document learnings from primary, secondary, and guardrail metrics.
3. Review recommendation quality and user feedback.
4. Create a revised hypothesis before testing another version.

## 12. Final Deliverable Checklist

- Clear fictional feature selected: **Smart Task Match**
- Clear, testable hypothesis included
- Control and variant are clearly described
- Primary and secondary metrics are defined
- Sample size and duration are estimated
- Risks and mitigations are documented
- Success, failure, and inconclusive rollout paths are defined
