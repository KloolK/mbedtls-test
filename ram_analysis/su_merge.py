#!/usr/bin/env python2

# This script requires python 2.7
# This script merges .su files into a single file

import os
import argparse

def run_main(repo_path, output_file_path):
    with open(output_file_path, "w") as output_file:
        for root, dirs, files in sorted(os.walk(repo_path)):
            for filepath in sorted(files):
                if not filepath.endswith(".su"):
                    continue
                with open(os.path.join(root, filepath), "r+b") as f:
                    f_content = f.read()
                    output_file.write("  " + filepath)
                    output_file.write("\n")
                    for line in f_content.splitlines():
                        output_file.write("    ")
                        cursor_loc = 4
                        for word in line.split():
                            if word.isdigit():
                                output_file.write(" " * max((84 - cursor_loc), 1))
                                output_file.write(word)
                                output_file.write(" " * max((6 - len(word)), 1))
                            else:
                                output_file.write(word)
                            cursor_loc += len(word)
                        output_file.write("\n")
                    output_file.write("\n")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description='Merge .su files into a single file.'
    )
    parser.add_argument(
        "repo_path", type=str, help="the path to the repository"
    )
    parser.add_argument(
        "output_file_path", type=str, help="the output file to be written", default="all_files.su"
    )
    merge_args = parser.parse_args()
    run_main(merge_args.repo_path, merge_args.output_file_path)
