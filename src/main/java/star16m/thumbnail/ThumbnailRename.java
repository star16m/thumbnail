package star16m.thumbnail;

import java.io.File;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.name.Rename;

import star16m.utils.file.FileUtil;

public class ThumbnailRename extends Rename {

	public static final String DEFAULT_THUMBNAIL_PREFIX = "";
	public static final String DEFAULT_THUMBNAIL_SUFFIX = "_thumbnail";
	private String prefixString = null;
	private String suffixString = null;
	public ThumbnailRename() {
		this(DEFAULT_THUMBNAIL_PREFIX, DEFAULT_THUMBNAIL_SUFFIX);
	}
	public ThumbnailRename(String prefixString, String suffixString) {
		this.prefixString = prefixString == null || prefixString.length() <= 0 ? DEFAULT_THUMBNAIL_PREFIX : prefixString;
		this.suffixString = suffixString == null || suffixString.length() <= 0 ? DEFAULT_THUMBNAIL_SUFFIX : suffixString;
	}
	public String getPrefix() {
		return this.prefixString;
	}
	public String getSuffix() {
		return this.suffixString;
	}
	public boolean isThumbnailFile(String fileName) {
		boolean isThumbnailStringContains = false;
		isThumbnailStringContains |= this.prefixString != null && this.prefixString.length() > 0 && fileName.contains(this.prefixString);
		isThumbnailStringContains |= this.suffixString != null && this.suffixString.length() > 0 && fileName.contains(this.suffixString);
		return isThumbnailStringContains;
	}
	public String apply(String s, ThumbnailParameter thumbnailparameter) {
		return appendSuffix(appendPrefix(s, prefixString), suffixString);
	}
	public String getRealFileName(File file) {
		return this.prefixString + FileUtil.getBaseName(file.getName()) + this.suffixString + "." + FileUtil.getFileExtension(file);
	}
}
