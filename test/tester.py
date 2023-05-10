import subprocess
import tempfile


class CLI_COLOR:
    GREEN = '\033[92m'
    RED = '\033[91m'
    BOLD = '\033[1m'
    END = '\033[0m'


java_command = "java -cp target/test-classes/ "
jabyinja_command = "java -jar target/jabyinja-*.jar "
# jabyinja_command = "java -cp target/classes/:target/test-classes/ com.zoltanbalazs.Main "

base_dir = "target/test-classes/"

own_tests = [
    "com/zoltanbalazs/Own/ArraylistTest",
    "com/zoltanbalazs/Own/Dup2",
    "com/zoltanbalazs/Own/Functional",
    "com/zoltanbalazs/Own/Inheritence",
    "com/zoltanbalazs/Own/Instanceof",
    "com/zoltanbalazs/Own/Multianewarray",
    "com/zoltanbalazs/Own/Nested",
    "com/zoltanbalazs/Own/Ownclass",
    "com/zoltanbalazs/Own/Template",
]

pti_basic_tests = [
    "com/zoltanbalazs/PTI/_01/Print",
    "com/zoltanbalazs/PTI/_02/_01/PointMain",
    "com/zoltanbalazs/PTI/_02/_02/CircleMain",
    "com/zoltanbalazs/PTI/_02/_03/CircleMain",
    "com/zoltanbalazs/PTI/_02/_04/ComplexMain",
    "com/zoltanbalazs/PTI/_02/_05/LineMain",
    "com/zoltanbalazs/PTI/_03/Main",
    "com/zoltanbalazs/PTI/_04/_02/Main",
    "com/zoltanbalazs/PTI/_05/_01/KisZH",
    "com/zoltanbalazs/PTI/_05/_02/Main",
    "com/zoltanbalazs/PTI/_05/_03/Main",
    "com/zoltanbalazs/PTI/_05/_04/IntVectorDemo",
    "com/zoltanbalazs/PTI/_08/_01/BookMain",
    "com/zoltanbalazs/PTI/_08/_02/CoffeeShop",
    "com/zoltanbalazs/PTI/_09/_01/Main",
    "com/zoltanbalazs/PTI/_09/_02/Main",
    "com/zoltanbalazs/PTI/_09/_03/Main",
    "com/zoltanbalazs/PTI/_09/_04/Main",
    "com/zoltanbalazs/PTI/_10/_01/KisZH",
    "com/zoltanbalazs/PTI/_10/_04/Swap",
    "com/zoltanbalazs/PTI/_11/_01/FlyingMain",
    "com/zoltanbalazs/PTI/_11/_02/Main",
    "com/zoltanbalazs/PTI/_11/_03/AnimalMain",
    "com/zoltanbalazs/PTI/_11/_04/AnimalMain",
    "com/zoltanbalazs/PTI/_12/KisZH",
]

passed_tests = 0
failed_tests = 0


def tester(test_files):
    longest_name = max(test_files, key=len)
    global passed_tests
    global failed_tests

    for test_file in test_files:
        jabyinja_file = tempfile.TemporaryFile()
        java_file = tempfile.TemporaryFile()

        subprocess.call(jabyinja_command + base_dir + test_file +
                        ".class", shell=True, stdout=jabyinja_file, stderr=jabyinja_file)

        subprocess.call(java_command + test_file.replace("/",
                        "."), shell=True, stdout=java_file, stderr=java_file)

        jabyinja_file.seek(0)
        java_file.seek(0)

        jabyinja_file_content = jabyinja_file.readlines()
        java_file_content = java_file.readlines()

        # print(jabyinja_file_content)
        # print(java_file_content)
        # print()

        is_valid = (jabyinja_file_content == java_file_content)

        align_length = len(longest_name) - len(test_file)
        if is_valid:
            print("\t - " + CLI_COLOR.BOLD + test_file + ": " + (' ' * align_length) +
                  CLI_COLOR.GREEN + "PASSED" + CLI_COLOR.END)
            passed_tests += 1
        else:
            print("\t - " + CLI_COLOR.BOLD + test_file + ": " + (' ' * align_length) +
                  CLI_COLOR.RED + "FAILED" + CLI_COLOR.END)
            failed_tests += 1

        jabyinja_file.close()
        java_file.close()


if __name__ == '__main__':
    print(CLI_COLOR.BOLD + "Own files: " + CLI_COLOR.END)
    tester(own_tests)
    print()

    print(CLI_COLOR.BOLD + "ELTE PTI Basic files: " + CLI_COLOR.END)
    tester(pti_basic_tests)
    print()

    print(CLI_COLOR.BOLD + "TOTAL:" + CLI_COLOR.END)
    print("\t" + CLI_COLOR.BOLD + CLI_COLOR.GREEN + "PASSED: " + CLI_COLOR.END +
          CLI_COLOR.BOLD + str(passed_tests) + CLI_COLOR.END)
    print("\t" + CLI_COLOR.BOLD + CLI_COLOR.RED + "FAILED: " + CLI_COLOR.END +
          CLI_COLOR.BOLD + str(failed_tests) + CLI_COLOR.END)
