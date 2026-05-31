# User Journey Map

**This task is part of Community Dreams Foundation (CDF) through the CDF Portal / TaskVerse.**

## Summary

This directory contains a visual user journey map for a fictional Instagram feature called **Close Friends Recap**.

The feature helps users turn recent photos and videos into a polished private recap for their Close Friends list. The journey map follows one primary persona from initial awareness through post-use reflection and identifies actions, thoughts, feelings, pain points, and opportunities at each stage.

## Task

**Prototype a User Journey Map for a New Feature**

## Task Description

Understanding the user's experience is crucial for product development. This task involves creating a visual user journey map for a specific new feature, identifying key touchpoints and pain points.

Required steps:

1. Select a fictional new feature for an existing popular app.
2. Identify a primary user persona for this feature by researching common user types for the chosen app.
3. Find an open-source user journey map template or tool.
4. Map out the user's journey for interacting with the new feature, from initial awareness to post-use.
5. Include at least 5-7 distinct stages, user actions, thoughts, feelings, and potential pain points/opportunities.
6. Highlight 2-3 key pain points or moments of delight in the journey.
7. Export the map as an image or share a link to the online board.

Deliverable:

A link to a shared online user journey map or an attached PNG/JPG image file of the map, posted in the task comment.

## Success Criteria

1. The user journey map link/file is accessible.
2. The map focuses on a single, clearly defined new feature.
3. A primary user persona is implicitly or explicitly represented.
4. The map includes at least 5 distinct stages of the user journey.
5. User actions, thoughts, and feelings are depicted for each stage.
6. At least 2 pain points or opportunities are identified.

## Files

- `instagram-close-friends-recap-journey-map.png` - Final exported journey map image.
- `journey-map-data.json` - Structured source data for the journey stages and persona.
- `UserJourneyMapGenerator.java` - Java program used to generate the PNG from the JSON data.
- `README.md` - Project summary, task details, and viewing instructions.

## Journey Map Coverage

- App: **Instagram**
- Fictional feature: **Close Friends Recap**
- Persona: **Maya, 24, privacy-conscious social sharer**
- Journey stages: Awareness, Consideration, Setup, Content Selection, Preview and Edit, Share, Post-Use
- Highlighted moments: privacy anxiety, editing overload, and delight from close-friend reactions
- Deliverable format: PNG image

## Selected Guide / Tool References

- Pew Research Center Social Media Fact Sheet: https://www.pewresearch.org/internet/fact-sheet/social-media/
- DataReportal Digital 2026: United States: https://datareportal.com/reports/digital-2026-united-states-of-america
- draw.io / diagrams.net GitHub: https://github.com/jgraph/drawio
- Service Design Tools Journey Map: https://servicedesigntools.org/tools/journey-map

## How to Regenerate the PNG

From this directory:

```powershell
javac UserJourneyMapGenerator.java
java UserJourneyMapGenerator journey-map-data.json instagram-close-friends-recap-journey-map.png
```

## Deliverable

Attach `instagram-close-friends-recap-journey-map.png` to the task comment in the CDF / TaskVerse portal.
