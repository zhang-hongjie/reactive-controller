package com.example.config;

import org.hibernate.envers.RevisionListener;
import org.slf4j.MDC;

import static com.example.config.MdcDataFilter.AUDIT_USERNAME;

public class CustomizedRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		RevinfoEntity rev = (RevinfoEntity) revisionEntity;
		rev.setRevuser(MDC.get(AUDIT_USERNAME));
	}
}