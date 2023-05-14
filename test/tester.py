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
    "com/zoltanbalazs/Own/Athrow",
    "com/zoltanbalazs/Own/Dup2",
    "com/zoltanbalazs/Own/Functional",
    "com/zoltanbalazs/Own/Inheritence",
    "com/zoltanbalazs/Own/Instanceof",
    "com/zoltanbalazs/Own/Multianewarray",
    "com/zoltanbalazs/Own/Nested",
    "com/zoltanbalazs/Own/Ownclass",
    "com/zoltanbalazs/Own/SwitchAthrow",
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
    "com/zoltanbalazs/PTI/_06/_02/AddByLine",
    "com/zoltanbalazs/PTI/_06/_04/Main",
    "com/zoltanbalazs/PTI/_08/_01/BookMain",
    "com/zoltanbalazs/PTI/_08/_02/CoffeeShop",
    "com/zoltanbalazs/PTI/_09/_01/Main",
    "com/zoltanbalazs/PTI/_09/_02/Main",
    "com/zoltanbalazs/PTI/_09/_03/Main",
    "com/zoltanbalazs/PTI/_09/_04/Main",
    "com/zoltanbalazs/PTI/_10/_01/KisZH",
    "com/zoltanbalazs/PTI/_10/_02/BookMain",
    "com/zoltanbalazs/PTI/_10/_03/BagMain",
    "com/zoltanbalazs/PTI/_10/_04/Swap",
    "com/zoltanbalazs/PTI/_11/_01/FlyingMain",
    "com/zoltanbalazs/PTI/_11/_02/Main",
    "com/zoltanbalazs/PTI/_11/_03/AnimalMain",
    "com/zoltanbalazs/PTI/_11/_04/AnimalMain",
    "com/zoltanbalazs/PTI/_12/KisZH",
]

pti_args_tests = [
    ("com/zoltanbalazs/PTI/_01/GCD", [ARG_TYPES.SHORT, ARG_TYPES.SHORT]),
    ("com/zoltanbalazs/PTI/_01/Greet", [ARG_TYPES.STRING]),
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
    ("com/zoltanbalazs/PTI/_06/_01/Calculator",
     [ARG_TYPES.DOUBLE, ARG_TYPES.CHAR, ARG_TYPES.DOUBLE]),
]

pti_args_stdin_tests = [
    ("com/zoltanbalazs/PTI/_06/_03/IsPartOf", [ARG_TYPES.STRING])
]

passed_tests = 0
failed_tests = 0


def tester(test_files, line_length, test_type):
    global passed_tests
    global failed_tests

    if test_type == TEST_TYPE.ARGS or test_type == TEST_TYPE.STDIN:
        number_of_tests = random.randint(3, 10)
    else:
        number_of_tests = 1

    for test_file in test_files:
        all_valid = True

        jabyinja_file = tempfile.TemporaryFile()
        java_file = tempfile.TemporaryFile()

        for _ in range(number_of_tests):

            if type(test_file) is tuple:
                (test_file, test_arg_types) = (test_file[0], test_file[1])

            args = ""
            if test_type == TEST_TYPE.ARGS or test_type == TEST_TYPE.STDIN:
                tmp_args = []

                for arg in test_arg_types:
                    if arg == ARG_TYPES.BYTE:
                        tmp_args.append(random.randint(-2 ** 7, 2 ** 7 - 1))
                    elif arg == ARG_TYPES.SHORT:
                        tmp_args.append(random.randint(-2 ** 15, 2 ** 15 - 1))
                    elif arg == ARG_TYPES.INT:
                        tmp_args.append(random.randint(-2 ** 31, 2 ** 31 - 1))
                    elif arg == ARG_TYPES.LONG:
                        tmp_args.append(random.randint(-2 ** 63, 2 ** 63 - 1))
                    elif arg == ARG_TYPES.FLOAT:
                        tmp_args.append(round(random.uniform(0, 100), 7))
                    elif arg == ARG_TYPES.DOUBLE:
                        tmp_args.append(round(random.uniform(0, 100), 15))
                    elif arg == ARG_TYPES.CHAR:
                        tmp_args.append(random.choice(string.printable))
                    elif arg == str:
                        tmp_args.append(''.join(random.choice(
                            string.printable) for _ in range(20)))

                for arg in tmp_args:
                    args = f'{args} \"{str(arg)}\"'

            args = args.strip()

            if test_type == TEST_TYPE.STDIN:
                subprocess.call("echo \"" + args + "\" | " + jabyinja_command + base_dir + test_file +
                                ".class", shell=True, stdout=jabyinja_file, stderr=jabyinja_file)

                subprocess.call("echo \"" + args + "\" | " + java_command + test_file.replace("/",
                                "."), shell=True, stdout=java_file, stderr=java_file)
            else:
                subprocess.call(jabyinja_command + base_dir + test_file +
                                ".class " + args, shell=True, stdout=jabyinja_file, stderr=jabyinja_file)

                subprocess.call(java_command + test_file.replace("/",
                                ".") + " " + args, shell=True, stdout=java_file, stderr=java_file)

            jabyinja_file.seek(0)
            java_file.seek(0)

            jabyinja_file_content = jabyinja_file.readlines()
            java_file_content = java_file.readlines()

            is_valid = (jabyinja_file_content == java_file_content)

            if not is_valid:
                # print(jabyinja_file_content)
                # print(java_file_content)
                all_valid = False

        jabyinja_file.close()
        java_file.close()

        align_length = len(line_length) - len(test_file)

        if all_valid:
            print("\t - " + CLI_COLOR.BOLD + test_file + ": " + (' ' * align_length) + CLI_COLOR.END +
                  CLI_COLOR.GREEN + "PASSED" + CLI_COLOR.END)
            passed_tests += 1
        else:
            print("\t - " + CLI_COLOR.BOLD + test_file + ": " + (' ' * align_length) + CLI_COLOR.END +
                  CLI_COLOR.RED + "FAILED" + CLI_COLOR.END)
            failed_tests += 1


if __name__ == '__main__':
    longest_name = max(own_tests + pti_basic_tests, key=len)
    longest_name_tuple = max(
        pti_args_tests + pti_stdin_tests, key=lambda item: len(item[0]))[0]
    longest_line = max(longest_name, longest_name_tuple)

    print(CLI_COLOR.BOLD + "Test files: " + CLI_COLOR.END)
    tester(own_tests, longest_line, TEST_TYPE.STANDARD)
    tester(pti_basic_tests, longest_line, TEST_TYPE.STANDARD)
    tester(pti_args_tests, longest_line, TEST_TYPE.ARGS)
    tester(pti_stdin_tests, longest_line, TEST_TYPE.STDIN)

    print(CLI_COLOR.BOLD + "TOTAL:" + CLI_COLOR.END)
    print("\t" + CLI_COLOR.BOLD + CLI_COLOR.GREEN + "PASSED: " + CLI_COLOR.END +
          CLI_COLOR.BOLD + str(passed_tests) + CLI_COLOR.END)
    print("\t" + CLI_COLOR.BOLD + CLI_COLOR.RED + "FAILED: " + CLI_COLOR.END +
          CLI_COLOR.BOLD + str(failed_tests) + CLI_COLOR.END)
