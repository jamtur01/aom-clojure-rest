SUBDIRS = apix apiy

.PHONY: subdirs $(SUBDIRS)
     
subdirs: $(SUBDIRS)
     
$(SUBDIRS):
	$(MAKE) -C $@ build

