package fr.almavivahealth.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.domain.entity.MomentDay.MomentDayBuilder;
import fr.almavivahealth.service.dto.MomentDayDTO;
import fr.almavivahealth.service.dto.MomentDayDTO.MomentDayDTOBuilder;

public abstract class MomentDayMapperDecorator implements MomentDayMapper {

	@Autowired
	private ContentRepository contentRepository;

	@Override
	public MomentDayDTO momentDayToMomentDayDTO(final MomentDay momentDay) {
		if (momentDay == null) {
			return null;
		}

		final MomentDayDTOBuilder momentDayDTO = MomentDayDTO.builder();
		momentDayDTO.name(momentDay.getName());
		momentDayDTO.id(momentDay.getId());
		momentDayDTO.contentIds(contentListToContentIdList(momentDay.getContents()));

		return momentDayDTO.build();
	}

	@Override
	public MomentDay momentDayDTOToMomentDay(final MomentDayDTO momentDayDTO) {
		if (momentDayDTO == null) {
			return null;
		}

		final MomentDayBuilder momentDay = MomentDay.builder();
		momentDay.name(momentDayDTO.getName());
		momentDay.id(momentDayDTO.getId());
		momentDay.contents(contentIdListToContentList(momentDayDTO.getContentIds()));

		return momentDay.build();
	}

	private List<Long> contentListToContentIdList(final List<Content> contents) {
		if (null == contents) {
			return Collections.emptyList();
		}

		final List<Long> contentDTOIds = new ArrayList<>(contents.size());
		for (final Content content : contents) {
			contentDTOIds.add(content.getId());
		}

		return contentDTOIds;
	}

	private List<Content> contentIdListToContentList(final List<Long> contentIds) {
		if (null == contentIds) {
			return Collections.emptyList();
		}

		final List<Content> contents = new ArrayList<>(contentIds.size());
		for (final Long contentId : contentIds) {
			final Optional<Content> content = contentRepository.findById(contentId);
			if (content.isPresent()) {
				final Content content2 = content.get();
				contents.add(content2);
			}
		}

		return contents;
	}
}
