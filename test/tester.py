import random
import string
import subprocess
import tempfile
from enum import Enum
from subprocess import PIPE, Popen


class CLI_COLOR:
    GREEN = '\033[92m'
    RED = '\033[91m'
    BOLD = '\033[1m'
    END = '\033[0m'


class TEST_TYPE(Enum):
    STANDARD = 0x0001,
    ARGS = 0x0010,
    STDIN = 0x0100,
    FILEIO = 0x1000,


class ARG_TYPES(Enum):
    BYTE = "byte"
    SHORT = "short",
    INT = "short",
    LONG = "long",
    FLOAT = "float",
    DOUBLE = "double",
    CHAR = "char",
    STRING = "string",


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

pti_args_tests = [
    ("com/zoltanbalazs/PTI/_01/GCD", [ARG_TYPES.SHORT, ARG_TYPES.SHORT]),
    ("com/zoltanbalazs/PTI/_01/Greet", [str]),
    ("com/zoltanbalazs/PTI/_01/Odd", [ARG_TYPES.INT]),
    ("com/zoltanbalazs/PTI/_01/PerfectNum", [ARG_TYPES.SHORT]),
    ("com/zoltanbalazs/PTI/_01/PerfectNumRange", [ARG_TYPES.BYTE]),
    ("com/zoltanbalazs/PTI/_01/TwoNum", [ARG_TYPES.INT, ARG_TYPES.INT]),
]

pti_stdin_tests = [
    ("com/zoltanbalazs/PTI/_01/Euler", [ARG_TYPES.INT]),
    ("com/zoltanbalazs/PTI/_01/Factorial", [ARG_TYPES.SHORT]),
    ("com/zoltanbalazs/PTI/_01/Half", [ARG_TYPES.INT, ARG_TYPES.INT]),
    ("com/zoltanbalazs/PTI/_01/Sqrt", [ARG_TYPES.INT]),
    ("com/zoltanbalazs/PTI/_01/SquareRoot",
     [ARG_TYPES.DOUBLE, ARG_TYPES.DOUBLE]),
]

passed_tests = 0
failed_tests = 0


def tester(test_files, test_type):
    longest_name = max(test_files, key=len)
    global passed_tests
    global failed_tests

    number_of_tests = 1
    if test_type == TEST_TYPE.ARGS or test_type == TEST_TYPE.STDIN:
        number_of_tests = random.randint(3, 10)

    for test_file in test_files:
        all_valid = True

        for _ in range(number_of_tests):
            jabyinja_file = tempfile.TemporaryFile()
            java_file = tempfile.TemporaryFile()

            args = ""
            if (test_type == TEST_TYPE.ARGS or test_type == TEST_TYPE.STDIN) and type(test_file) is tuple:
                tmp_args = []
                (test_file, test_arg_types) = (test_file[0], test_file[1])
                for arg in test_arg_types:
                    if arg == ARG_TYPES.BYTE:
                        tmp_args.append(random.randint(-2 ** 7, 2 ** 7 - 1))
                    elif arg == ARG_TYPES.SHORT:
                        tmp_args.append(random.randint(-2 ** 15, 2 ** 15 - 1))
                    elif arg == ARG_TYPES.INT:
                        tmp_args.append(random.randint(-2 ** 31, 2 ** 31 - 1))
                    elif arg == ARG_TYPES.DOUBLE:
                        tmp_args.append(round(random.uniform(-100, 100), 15))
                    elif arg == str:
                        tmp_args.append(''.join(random.choice(
                            string.ascii_letters) for _ in range(20)))

                args = " ".join(str(arg) for arg in tmp_args)
                args += " "
            elif test_type == TEST_TYPE.ARGS or test_type == TEST_TYPE.STDIN:
                tmp_args = []
                for arg in test_arg_types:
                    if arg == ARG_TYPES.BYTE:
                        tmp_args.append(random.randint(-2 ** 7, 2 ** 7 - 1))
                    elif arg == ARG_TYPES.SHORT:
                        tmp_args.append(random.randint(-2 ** 15, 2 ** 15 - 1))
                    elif arg == ARG_TYPES.INT:
                        tmp_args.append(random.randint(-2 ** 31, 2 ** 31 - 1))
                    elif arg == ARG_TYPES.DOUBLE:
                        tmp_args.append(round(random.uniform(-100, 100), 15))
                    elif arg == str:
                        tmp_args.append(''.join(random.choice(
                            string.ascii_letters) for _ in range(20)))

                args = " ".join(str(arg) for arg in tmp_args)
                args += " "

            if test_type == TEST_TYPE.STDIN:
                Popen([jabyinja_command.split(" ")[0], "-jar", ' '.join(jabyinja_command.split(" ")[2:]).strip().replace('*', '0.9.1'), base_dir + test_file +
                      ".class"], stdin=PIPE, stdout=jabyinja_file, stderr=jabyinja_file).communicate(input=str.encode(args))

                Popen([java_command.split(" ")[0], "-cp", ' '.join(java_command.split(" ")[2:]) + ' ' + test_file.replace(
                    "/", ".")], stdin=PIPE, stdout=java_file, stderr=java_file).communicate(input=str.encode(args))
            else:
                subprocess.call(jabyinja_command + base_dir + test_file +
                                ".class " + args, shell=True, stdout=jabyinja_file, stderr=jabyinja_file)

                subprocess.call(java_command + test_file.replace("/",
                                ".") + " " + args, shell=True, stdout=java_file, stderr=java_file)

            jabyinja_file.seek(0)
            java_file.seek(0)

            jabyinja_file_content = jabyinja_file.readlines()
            java_file_content = java_file.readlines()

            print(jabyinja_file_content)
            print(java_file_content)
            print()

            is_valid = (jabyinja_file_content == java_file_content)

            align_length = len(longest_name) - len(test_file)
            if is_valid:
                print("\t - " + CLI_COLOR.BOLD + test_file + " " + args + (' ' * align_length) +
                      CLI_COLOR.GREEN + "PASSED" + CLI_COLOR.END)
            else:
                print("\t - " + CLI_COLOR.BOLD + test_file + " " + args + (' ' * align_length) +
                      CLI_COLOR.RED + "FAILED" + CLI_COLOR.END)
                all_valid = False

            jabyinja_file.close()
            java_file.close()

        if all_valid:
            passed_tests += 1
        else:
            failed_tests += 1


if __name__ == '__main__':
    print(CLI_COLOR.BOLD + "Own files: " + CLI_COLOR.END)
    tester(own_tests, TEST_TYPE.STANDARD)
    print()

    print(CLI_COLOR.BOLD + "ELTE PTI Basic files: " + CLI_COLOR.END)
    tester(pti_basic_tests, TEST_TYPE.STANDARD)
    print()

    print(CLI_COLOR.BOLD + "ELTE PTI Args files: " + CLI_COLOR.END)
    tester(pti_args_tests, TEST_TYPE.ARGS)
    print()

    print(CLI_COLOR.BOLD + "ELTE PTI STDIN files: " + CLI_COLOR.END)
    tester(pti_stdin_tests, TEST_TYPE.STDIN)
    print()

    print(CLI_COLOR.BOLD + "TOTAL:" + CLI_COLOR.END)
    print("\t" + CLI_COLOR.BOLD + CLI_COLOR.GREEN + "PASSED: " + CLI_COLOR.END +
          CLI_COLOR.BOLD + str(passed_tests) + CLI_COLOR.END)
    print("\t" + CLI_COLOR.BOLD + CLI_COLOR.RED + "FAILED: " + CLI_COLOR.END +
          CLI_COLOR.BOLD + str(failed_tests) + CLI_COLOR.END)
