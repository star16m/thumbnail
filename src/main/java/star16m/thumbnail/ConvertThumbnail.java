package star16m.thumbnail;

import java.io.File;

import net.coobird.thumbnailator.Thumbnails;

/**
 * ConvertThumbnail
 */
public class ConvertThumbnail {
	public static final ThumbnailRename DEFAULT_SUFFIX_THUMBNAIL_RENAME = new ThumbnailRename();
	public static final int DEFAULT_THUMBNAIL_WIDTH = 50;
	public static final int DEFAULT_THUMBNAIL_HEIGHT = 23;
	public static final boolean DEFAULT_THUMBNAIL_OVERWRITE = false;
	private File targetFile =  null;
	private ThumbnailRename rename = null;
	private boolean overwrite;
	private int thumbnailWidth;
	private int thumbnailHeight;
	public ConvertThumbnail(File targetFile) throws Exception {
		this(targetFile, DEFAULT_SUFFIX_THUMBNAIL_RENAME);
	}
	public ConvertThumbnail(File targetFile, ThumbnailRename rename) throws Exception {
		this(targetFile, rename, DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, DEFAULT_THUMBNAIL_OVERWRITE);
	}
	public ConvertThumbnail(File targetFile, int thumbnailWidth, int thumbnailHeight) throws Exception {
		this(targetFile, DEFAULT_SUFFIX_THUMBNAIL_RENAME, thumbnailWidth, thumbnailHeight, DEFAULT_THUMBNAIL_OVERWRITE);
	}
	public ConvertThumbnail(File targetFile, ThumbnailRename rename, int thumbnailWidth, int thumbnailHeight) throws Exception {
		this(targetFile, rename, thumbnailWidth, thumbnailHeight, DEFAULT_THUMBNAIL_OVERWRITE);
	}
	public ConvertThumbnail(File targetFile, ThumbnailRename rename, int thumbnailWidth, int thumbnailHeight, boolean overwrite) throws Exception {
		this.targetFile = targetFile;
		this.rename = rename;
		this.thumbnailWidth = thumbnailWidth;
		this.thumbnailHeight = thumbnailHeight;
		this.overwrite = overwrite;
		
		if (this.rename == null) {
			this.rename = DEFAULT_SUFFIX_THUMBNAIL_RENAME;
		}
		if (this.thumbnailWidth < 1) {
			this.thumbnailWidth = DEFAULT_THUMBNAIL_WIDTH;
		}
		if (this.thumbnailHeight < 1) {
			this.thumbnailHeight = DEFAULT_THUMBNAIL_HEIGHT;
		}
	}
	public int convert() throws Exception {
		File[] targetFiles = new ImageFileFinder().getImageFiles(this.targetFile, this.rename, this.overwrite);
		if (targetFiles != null && targetFiles.length > 0) {
			Thumbnails.of(targetFiles).size(thumbnailWidth, thumbnailHeight).toFiles(this.rename);
		}
		return targetFiles == null ? 0 : targetFiles.length;
	}
}
