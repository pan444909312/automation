#!/usr/bin/env python3
"""
简单的测试用例评估脚本
"""
import json
import re
from pathlib import Path

def grade_eval_1(testcase_file):
    """评估 eval-1: 登录功能测试用例"""
    content = testcase_file.read_text()
    results = []

    # 1. 生成了markdown文件
    results.append({
        "text": "生成了markdown文件",
        "passed": testcase_file.exists(),
        "evidence": f"文件存在: {testcase_file.exists()}"
    })

    # 2. 包含至少5个测试用例
    tc_count = len(re.findall(r'### TC-\w+-\d+', content))
    results.append({
        "text": "包含至少5个测试用例",
        "passed": tc_count >= 5,
        "evidence": f"找到 {tc_count} 个测试用例"
    })

    # 3. 每个用例都有用例ID
    has_ids = bool(re.search(r'TC-\w+-\d+', content))
    results.append({
        "text": "每个用例都有用例ID",
        "passed": has_ids,
        "evidence": f"用例ID格式正确: {has_ids}"
    })

    # 4. 每个用例都有优先级
    priority_count = len(re.findall(r'\*\*优先级\*\*：P[0-3]', content))
    results.append({
        "text": "每个用例都有优先级",
        "passed": priority_count >= 5,
        "evidence": f"找到 {priority_count} 个优先级标记"
    })

    # 5. 每个用例都有前置条件
    precondition_count = len(re.findall(r'\*\*前置条件\*\*：', content))
    results.append({
        "text": "每个用例都有前置条件",
        "passed": precondition_count >= 5,
        "evidence": f"找到 {precondition_count} 个前置条件"
    })

    # 6. 每个用例都有测试步骤
    steps_count = len(re.findall(r'\*\*测试步骤\*\*：', content))
    results.append({
        "text": "每个用例都有测试步骤",
        "passed": steps_count >= 5,
        "evidence": f"找到 {steps_count} 个测试步骤"
    })

    # 7. 每个用例都有预期结果
    expected_count = len(re.findall(r'\*\*预期结果\*\*：', content))
    results.append({
        "text": "每个用例都有预期结果",
        "passed": expected_count >= 5,
        "evidence": f"找到 {expected_count} 个预期结果"
    })

    # 8. 覆盖了正向流程
    has_positive = bool(re.search(r'\[正向\]', content)) and bool(re.search(r'P0', content))
    results.append({
        "text": "覆盖了正向流程",
        "passed": has_positive,
        "evidence": f"包含正向P0用例: {has_positive}"
    })

    # 9. 覆盖了异常场景
    has_exception = bool(re.search(r'锁定|未注册', content))
    results.append({
        "text": "覆盖了异常场景",
        "passed": has_exception,
        "evidence": f"包含异常场景: {has_exception}"
    })

    # 10. 覆盖了边界值
    has_boundary = bool(re.search(r'\[边界\]|边界值', content))
    results.append({
        "text": "覆盖了边界值",
        "passed": has_boundary,
        "evidence": f"包含边界值测试: {has_boundary}"
    })

    return results

def grade_eval_2(testcase_file):
    """评估 eval-2: 搜索功能测试用例"""
    content = testcase_file.read_text()
    results = []

    results.append({
        "text": "生成了markdown文件",
        "passed": testcase_file.exists(),
        "evidence": f"文件存在: {testcase_file.exists()}"
    })

    has_both_types = bool(re.search(r'## 功能测试用例', content)) and bool(re.search(r'## 接口测试用例', content))
    results.append({
        "text": "包含功能测试和接口测试两类",
        "passed": has_both_types,
        "evidence": f"包含两类测试: {has_both_types}"
    })

    tc_count = len(re.findall(r'### TC-\w+-', content))
    results.append({
        "text": "包含至少8个测试用例",
        "passed": tc_count >= 8,
        "evidence": f"找到 {tc_count} 个测试用例"
    })

    has_api_params = bool(re.search(r'/api/search\?', content))
    results.append({
        "text": "接口测试用例包含请求参数",
        "passed": has_api_params,
        "evidence": f"包含API请求参数: {has_api_params}"
    })

    has_response = bool(re.search(r'响应|code|data', content))
    results.append({
        "text": "接口测试用例包含响应验证",
        "passed": has_response,
        "evidence": f"包含响应验证: {has_response}"
    })

    has_length_boundary = bool(re.search(r'1个字符|50个字符|超过50', content))
    results.append({
        "text": "覆盖了搜索词长度边界",
        "passed": has_length_boundary,
        "evidence": f"包含长度边界测试: {has_length_boundary}"
    })

    has_sort = bool(re.search(r'排序|价格|销量', content))
    results.append({
        "text": "覆盖了排序功能",
        "passed": has_sort,
        "evidence": f"包含排序测试: {has_sort}"
    })

    has_no_result = bool(re.search(r'无结果|暂无相关商品', content))
    results.append({
        "text": "覆盖了无结果场景",
        "passed": has_no_result,
        "evidence": f"包含无结果场景: {has_no_result}"
    })

    has_history = bool(re.search(r'搜索历史|历史记录', content))
    results.append({
        "text": "覆盖了搜索历史功能",
        "passed": has_history,
        "evidence": f"包含搜索历史测试: {has_history}"
    })

    return results

def grade_eval_3(testcase_file):
    """评估 eval-3: 购物车功能测试用例"""
    content = testcase_file.read_text()
    results = []

    results.append({
        "text": "生成了markdown文件",
        "passed": testcase_file.exists(),
        "evidence": f"文件存在: {testcase_file.exists()}"
    })

    has_both_types = bool(re.search(r'## 功能测试用例', content)) and bool(re.search(r'## UI测试用例', content))
    results.append({
        "text": "包含功能测试和UI测试",
        "passed": has_both_types,
        "evidence": f"包含功能和UI测试: {has_both_types}"
    })

    tc_count = len(re.findall(r'### TC-\w+-', content))
    results.append({
        "text": "包含至少10个测试用例",
        "passed": tc_count >= 10,
        "evidence": f"找到 {tc_count} 个测试用例"
    })

    has_add_cart = bool(re.search(r'重复添加|数量累加', content))
    results.append({
        "text": "覆盖了加购和数量累加",
        "passed": has_add_cart,
        "evidence": f"包含加购累加测试: {has_add_cart}"
    })

    has_limit = bool(re.search(r'99|100', content))
    results.append({
        "text": "覆盖了数量上限",
        "passed": has_limit,
        "evidence": f"包含数量上限测试: {has_limit}"
    })

    has_offline = bool(re.search(r'下架|置灰', content))
    results.append({
        "text": "覆盖了下架商品状态",
        "passed": has_offline,
        "evidence": f"包含下架商品测试: {has_offline}"
    })

    has_batch = bool(re.search(r'批量删除|全选', content))
    results.append({
        "text": "覆盖了批量操作",
        "passed": has_batch,
        "evidence": f"包含批量操作测试: {has_batch}"
    })

    has_badge = bool(re.search(r'角标|导航栏', content))
    results.append({
        "text": "UI测试包含角标验证",
        "passed": has_badge,
        "evidence": f"包含角标验证: {has_badge}"
    })

    has_specific_steps = bool(re.search(r'点击|输入|查看|勾选', content))
    results.append({
        "text": "测试步骤具体可执行",
        "passed": has_specific_steps,
        "evidence": f"包含具体操作步骤: {has_specific_steps}"
    })

    return results

def main():
    workspace = Path(__file__).parent

    # 评估 eval-1
    eval1_file = workspace / "eval-1/with_skill/outputs/testcases.md"
    if eval1_file.exists():
        results = grade_eval_1(eval1_file)
        grading = {
            "eval_id": 1,
            "expectations": results,
            "pass_count": sum(1 for r in results if r["passed"]),
            "total_count": len(results)
        }
        output_file = workspace / "eval-1/with_skill/grading.json"
        output_file.write_text(json.dumps(grading, indent=2, ensure_ascii=False))
        print(f"Eval-1: {grading['pass_count']}/{grading['total_count']} passed")

    # 评估 eval-2
    eval2_file = workspace / "eval-2/with_skill/outputs/testcases.md"
    if eval2_file.exists():
        results = grade_eval_2(eval2_file)
        grading = {
            "eval_id": 2,
            "expectations": results,
            "pass_count": sum(1 for r in results if r["passed"]),
            "total_count": len(results)
        }
        output_file = workspace / "eval-2/with_skill/grading.json"
        output_file.write_text(json.dumps(grading, indent=2, ensure_ascii=False))
        print(f"Eval-2: {grading['pass_count']}/{grading['total_count']} passed")

    # 评估 eval-3
    eval3_file = workspace / "eval-3/with_skill/outputs/testcases.md"
    if eval3_file.exists():
        results = grade_eval_3(eval3_file)
        grading = {
            "eval_id": 3,
            "expectations": results,
            "pass_count": sum(1 for r in results if r["passed"]),
            "total_count": len(results)
        }
        output_file = workspace / "eval-3/with_skill/grading.json"
        output_file.write_text(json.dumps(grading, indent=2, ensure_ascii=False))
        print(f"Eval-3: {grading['pass_count']}/{grading['total_count']} passed")

if __name__ == "__main__":
    main()
