package star16m.thumbnail;

import java.io.File;
import java.io.FilenameFilter;

import star16m.utils.file.FileUtil;

public class ImageFileFinder {

	public File[] getImageFiles(File targetFile, final ThumbnailRename rename, final boolean overwrite) {
		if (targetFile == null || !targetFile.exists()) {
			throw new IllegalArgumentException();
		}
		if (targetFile.isDirectory()) {
			return targetFile.listFiles(new FilenameFilter() {
				public boolean accept(File targetFile, String fileName) {
					if (fileName == null || fileName.length() <= 0 || rename.isThumbnailFile(fileName)) {
						return false;
					}
					if (isAlreadyExistsThumbnailFile(new File(targetFile, fileName), rename) && !overwrite) {
						System.out.println("\talready exists thumbnail file. original file = [" + fileName + "]");
						return false;
					}
					String extension = FileUtil.getFileExtension(fileName).toLowerCase();
					return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
				}
			});
		} else {
			if (isAlreadyExistsThumbnailFile(targetFile, rename) && !overwrite) {
				System.out.println("\talready exists thumbnail file. original file = [" + targetFile.getName() + "]");
				return new File[]{};
			}
			return new File[] {targetFile};
		}
	}

	public boolean isAlreadyExistsThumbnailFile(File srcFile, final ThumbnailRename rename) {
		return new File(srcFile.getParentFile(), rename.getRealFileName(srcFile)).exists();
	}
}
